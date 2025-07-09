import { configureStore } from '@reduxjs/toolkit';
import { setupListeners } from '@reduxjs/toolkit/query';
import authReducer from '@/features/auth/authSlice';
import counterReducer from '@/features/counter/counterSlice';
import { apiService } from '@/lib/api';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    counter: counterReducer,
    [apiService.reducerPath]: apiService.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        // Date 객체나 함수 등 직렬화할 수 없는 값들을 무시
        ignoredActions: ['persist/PERSIST'],
        ignoredPaths: ['auth.user'],
      },
    }).concat(apiService.middleware),
  devTools: process.env.NODE_ENV !== 'production',
});

// Redux Toolkit Query 리스너 설정
setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
