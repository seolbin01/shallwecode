package com.shallwecode.backend.problem.application.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class CustomClassLoader extends ClassLoader {
    private byte[] loadClassData(String className) {
        String classFilePath = "temp/" + className + ".class";
        try {
            Path path = Paths.get(classFilePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException("Class " + name + " not found");
        }
        return defineClass(name, classData, 0, classData.length);
    }
}
