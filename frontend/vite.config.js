import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173, // 그대로 유지
    proxy: {
      "/makemyday": {
        target: "http://localhost:8080", // 스프링 서버 주소
        changeOrigin: true,
      },
    },
  },
});
