import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api/v1/': ' https://parana-hart-6c0dd51d52f9.herokuapp.com',
    },
  },
});
