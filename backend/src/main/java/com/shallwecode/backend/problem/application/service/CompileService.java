package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.CompileReqDTO;
import com.shallwecode.backend.problem.application.dto.CompileResDTO;
import com.shallwecode.backend.problem.application.dto.compile.SubmitResDTO;
import com.shallwecode.backend.problem.application.dto.problem.ProblemOneResDTO;
import com.shallwecode.backend.problem.application.dto.problem.TestcaseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.tools.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompileService {

    // 임시 디렉토리 경로
    private static final String TEMP_DIR = "temp";

    public CompileResDTO runCode(CompileReqDTO compileReqDTO) {

        String language = compileReqDTO.getLanguage();

        CompileResDTO compileResDTO = new CompileResDTO();

        if (language.equals("java")) {
            compileResDTO = compileJava(compileReqDTO.getCode());
        } else if (language.equals("python")){
//            compileResDTO = compilePython(compileReqDTO.getCode());
        } else {
            System.out.println("없는 언어입니다.");
        }

        return compileResDTO;
    }


    public SubmitResDTO checkTestCase(String code, ProblemOneResDTO problemOneResDTO) {
        int total = problemOneResDTO.getTestcases().size();
        log.info("{}",total);
        int correct = 0;
        for (TestcaseDTO testcaseDTO : problemOneResDTO.getTestcases()) {
            log.info("test dto: {}",testcaseDTO);
            log.info("testOutput: {}", testcaseDTO.getOutput());

            CompileResDTO compileResDTO = compileSubmitJava(code, testcaseDTO.getInput());
            log.info("compileResDTO: {}", compileResDTO.getOutput());
            if (testcaseDTO.getOutput().trim().equals(compileResDTO.getOutput().trim())) {
                correct++;
            }
        }
        boolean result = total == correct;

        double score = (double) correct / total * 100;
        log.info("correct : {}", correct);
        score = Math.round(score);
        log.info("score: {}", score);
        return new SubmitResDTO(result, score);
    }

    private CompileResDTO compileJava(String code) {

        CompileResDTO result = new CompileResDTO();

        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

            JavaFileObject sourceFile = new JavaSourceFromString("Solution", code);
            Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceFile);

            List<String> options = Arrays.asList("-d", TEMP_DIR);

            JavaCompiler.CompilationTask task = compiler.getTask(
                    null,
                    fileManager,
                    diagnostics,
                    options,
                    null,
                    compilationUnits
            );

            boolean success = task.call();

            if (!success) {
                StringBuilder errorMsg = new StringBuilder();
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    errorMsg.append(diagnostic.getMessage(null))
                            .append(" Line: ")
                            .append(diagnostic.getLineNumber())
                            .append("\n");
                }
                result.setCompileError(errorMsg.toString());
                return result;
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            PrintStream oldOut = System.out;
            System.setOut(printStream);

            try {
                CustomClassLoader classLoader = new CustomClassLoader();
                Class<?> cls = classLoader.loadClass("Solution");
                cls.getDeclaredMethod("main", String[].class)
                        .invoke(null, (Object) new String[]{});

                result.setOutput(outputStream.toString());
            } catch (Exception e) {
                result.setRuntimeError(e.getMessage());
            } finally {
                System.setOut(oldOut);
                cleanupFiles();
            }

        } catch (Exception e) {
            result.setSystemError(e.getMessage());
        }

        return result;
    }

    private void cleanupFiles() {
        try {
            Files.deleteIfExists(Paths.get(TEMP_DIR, "Solution" + ".class"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CompileResDTO compileSubmitJava(String code, String input) {
        CompileResDTO result = new CompileResDTO();

        try {
            // 1. 자바 컴파일러 가져오기
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

            // 2. 소스 코드를 JavaFileObject로 변환
            JavaFileObject sourceFile = new JavaSourceFromString("Solution", code);
            Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceFile);

            // 3. 컴파일 옵션 설정
            List<String> options = Arrays.asList("-d", TEMP_DIR);

            // 4. 컴파일 작업 실행
            JavaCompiler.CompilationTask task = compiler.getTask(
                    null,
                    fileManager,
                    diagnostics,
                    options,
                    null,
                    compilationUnits
            );

            boolean success = task.call();

            // 5. 컴파일 에러 수집
            if (!success) {
                StringBuilder errorMsg = new StringBuilder();
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    errorMsg.append(diagnostic.getMessage(null))
                            .append(" Line: ")
                            .append(diagnostic.getLineNumber())
                            .append("\n");
                }
                result.setCompileError(errorMsg.toString());
                return result;
            }

            // 6. 프로그램 실행 및 입출력 캡처
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

            PrintStream printStream = new PrintStream(outputStream);

            // 원래의 표준 입출력 저장
            PrintStream oldOut = System.out;
            InputStream oldIn = System.in;

            // 새로운 표준 입출력 설정
            System.setOut(printStream);
            System.setIn(inputStream);

            try {
                // 컴파일된 클래스 로드 및 실행
                CustomClassLoader classLoader = new CustomClassLoader();
                Class<?> cls = classLoader.loadClass("Solution");

                // main 메소드 실행 (빈 배열 전달)
                cls.getDeclaredMethod("main", String[].class)
                        .invoke(null, (Object) new String[]{});

                result.setOutput(outputStream.toString());

            } catch (Exception e) {
                result.setRuntimeError(e.getMessage());
            } finally {
                // 원래의 표준 입출력 복구
                System.setOut(oldOut);
                System.setIn(oldIn);
                cleanupFiles();
            }

        } catch (Exception e) {
            result.setSystemError(e.getMessage());
        }

        return result;
    }

}
