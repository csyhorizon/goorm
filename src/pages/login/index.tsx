
import React from 'react';
import { LoginForm } from '../components/LoginForm';

const Login = () => {
  return (
    <div className="min-h-screen bg-instagram-dark flex">
      {/* Left side - Image Preview */}
      <div className="hidden lg:flex lg:flex-1 items-center justify-center p-8 bg-gradient-to-br from-instagram-dark to-instagram-gray">
        <div className="relative w-80 h-96">
          {/* Mobile mockup container */}
          <div className="absolute inset-0 bg-instagram-border rounded-3xl p-2">
            <div className="w-full h-full bg-instagram-dark rounded-2xl overflow-hidden relative">
              {/* Overlapping story images */}
              <div className="absolute top-8 left-4 w-64 h-80 transform rotate-3">
                <img 
                  src="https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?ixlib=rb-4.0.3&auto=format&fit=crop&w=400&q=80"
                  alt="운동하는 사람"
                  className="w-full h-full object-cover rounded-xl shadow-lg"
                />
              </div>
              <div className="absolute top-12 left-8 w-64 h-80 transform -rotate-2">
                <img 
                  src="https://images.unsplash.com/photo-1594736797933-d0d6bbd4f6bd?ixlib=rb-4.0.3&auto=format&fit=crop&w=400&q=80"
                  alt="셀카"
                  className="w-full h-full object-cover rounded-xl shadow-lg"
                />
              </div>
              <div className="absolute top-6 left-12 w-64 h-80 transform rotate-1">
                <img 
                  src="https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?ixlib=rb-4.0.3&auto=format&fit=crop&w=400&q=80"
                  alt="음식"
                  className="w-full h-full object-cover rounded-xl shadow-lg"
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Right side - Login Form */}
      <div className="flex-1 lg:max-w-md xl:max-w-lg flex items-center justify-center p-8">
        <LoginForm />
      </div>
    </div>
  );
};

export default Login;
