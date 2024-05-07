FROM node:latest 
WORKDIR /app

COPY package*.json ./


RUN npm install --legacy-peer-deps

COPY . .

RUN npm run build

FROM nginx:latest

COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=0 /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

