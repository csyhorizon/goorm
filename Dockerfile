FROM node:23.10.0-alpine AS builder

WORKDIR /app

COPY package.json yarn.lock* package-lock.json* ./

RUN npm install

COPY . .

RUN npm run build

FROM node:23.10.0-alpine

WORKDIR /app

COPY --from=builder /app/.next ./.next
COPY --from=builder /app/public ./public
COPY --from=builder /app/package.json ./package.json

RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs
USER nextjs

CMD ["npm", "run", "start"]

EXPOSE 3000