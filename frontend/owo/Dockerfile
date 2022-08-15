FROM node:16 as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install --force
COPY . .
RUN npm run build

EXPOSE 8080

FROM nginx:stable-alpine as production-stage
#컨테이너 안에 nginx.conf를 옆 해당겨로에 옮겨준다.
COPY  ./nginx/nginx.conf /etc/nginx/conf.d/default.conf 

COPY --from=build-stage /app/dist /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]
