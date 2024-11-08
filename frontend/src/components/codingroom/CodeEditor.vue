<script setup>
import {ref, onMounted, shallowRef} from "vue";
import * as Y from 'yjs';
import {WebsocketProvider} from "y-websocket";
import {MonacoBinding} from "y-monaco";
import * as monaco from 'monaco-editor';
import axios from "axios";

const MONACO_EDITOR_OPTIONS = {
  automaticLayout: true,
  formatOnType: true,
  formatOnPaste: true,
  theme: 'vs-dark'
};

const languages = [
  {id: 'java', name: 'JAVA'},
  {id: 'python', name: 'Python'}
];

const code = ref('// Java code here...');
const editorInstance = shallowRef(null);
const showDropdown = ref(false);
const selectedLanguage = ref(languages[0]);
const isRunning = ref(false);
const monacoEl = ref(null);

onMounted(() => {
  const ydocument = new Y.Doc();
  const provider = new WebsocketProvider('ws://localhost:1234', 'monaco', ydocument);
  const type = ydocument.getText('monaco');

  const editor = monaco.editor.create(monacoEl.value, {
    value: code.value,
    language: selectedLanguage.value.id.toLowerCase(),
    ...MONACO_EDITOR_OPTIONS
  });

  editorInstance.value = editor;

  const binding = new MonacoBinding(
      type,
      editor.getModel(),
      new Set([editor]),
      provider.awareness
  );

  editor.onDidChangeModelContent(() => {
    code.value = editor.getValue();
  });
});

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value;
};

const selectLanguage = (language) => {
  selectedLanguage.value = language;
  showDropdown.value = false;

  if (editorInstance.value) {
    monaco.editor.setModelLanguage(
        editorInstance.value.getModel(),
        language.id.toLowerCase()
    );

    const defaultCode = language.id === 'python' ?
        '# Python code here...' :
        '// Java code here...';

    editorInstance.value.setValue(defaultCode);
  }
};

const runCode = async () => {
  isRunning.value = true;
  try {

    if (editorInstance.value) {
      code.value = editorInstance.value.getValue();
    }

    const response = await axios.post('http://localhost:8080/api/v1/compile/run', {
      code: code.value,
      language: selectedLanguage.value.id.toLowerCase()
    });

    console.log(response.data);

  } catch (error) {
    console.error('코드 실행 중 에러가 발생했습니다. ', error);
  } finally {
    isRunning.value = false;
  }
};
</script>

<template>
  <div class="code-container">
    <div class="setting">
      <div class="language-selector" @click="toggleDropdown">
        <div class="selected-language">
          {{ selectedLanguage.name }}
          <span class="dropdown-icon">▼</span>
        </div>
        <div v-if="showDropdown" class="dropdown-menu">
          <div
              v-for="lang in languages"
              :key="lang.id"
              class="dropdown-item"
              @click="selectLanguage(lang)"
          >
            {{ lang.name }}
          </div>
        </div>
      </div>
      <button
          class="run-button"
          @click="runCode"
          :disabled="isRunning"
      >
        <span class="run-text">{{ isRunning ? '실행중...' : '실행' }}</span>
        <span v-if="!isRunning" class="run-icon" :class="{ 'spinning': isRunning }">▶</span>
        <span v-else class="loader"></span>
      </button>
    </div>
    <div ref="monacoEl" class="editor-container"></div>
  </div>
</template>

<style scoped>
.code-container {
  flex: 1;
  width: 50%;
  min-width: 600px;
  height: 640px;
}

.setting {
  background-color: var(--editor-color);
  color: var(--background-color);
  height: 40px;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px var(--background-color) solid;
}

.editor-container {
  height: 600px;
}

.language-selector {
  position: relative;
  cursor: pointer;
  user-select: none;
}

.selected-language {
  display: flex;
  align-items: center;
  gap: 8px;
}

.dropdown-icon {
  font-size: 0.8em;
  opacity: 0.7;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  background-color: var(--editor-color);
  border: 1px solid var(--background-color);
  border-radius: 4px;
  margin-top: 4px;
  min-width: 80px;
  z-index: 1000;
}

.dropdown-item {
  padding: 8px 16px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.dropdown-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.run-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 12px;
  border: none;
  border-radius: 4px;
  background-color: transparent;
  color: var(--background-color);
  cursor: pointer;
  font-size: 0.9em;
  transition: background-color 0.2s;
}

.run-button:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.run-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.run-text {
  font-weight: 500;
}

.run-icon {
  font-size: 0.8em;
  margin-bottom: 2px;
}

.loader {
  width: 16px;
  height: 16px;
  border: 2px solid var(--background-color);
  border-bottom-color: transparent;
  border-radius: 50%;
  display: inline-block;
  animation: rotation 1s linear infinite;
}

@keyframes rotation {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>