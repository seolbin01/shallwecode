# Node.js LTS 버전이 설치된 Alpine Linux 이미지를 가져온다.
FROM node:lts-alpine

# 컨테이너 내부의 작업 디렉토리를 /app으로 설정한다.
WORKDIR /app

# 현재 디렉토리 내의 모든 파일과 폴더를 컨테이너의 /app 디렉토리로 복사한다.
COPY . .

# npm을 사용하여 프로젝트의 종속성을 설치한다.
RUN npm install

# 컨테이너가 시작될 때 node index.js 명령을 실행한다.
CMD ["node", "index.js"]