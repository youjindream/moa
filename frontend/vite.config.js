import { defineConfig, loadEnv } from 'vite';

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '');
  const proxy = {
    '/api/': {
      target: env.MOA_API_TARGET || 'http://localhost:8080',
      changeOrigin: true,
      rewrite: path => path.replace(/^\/api/, ''),
    },
  };
  return { server: { proxy }, preview: { proxy } };
});
