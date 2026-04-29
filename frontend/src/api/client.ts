import axios from 'axios'

const apiClient = axios.create({
  baseURL: '/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
})

// Global response error interceptor — logs structured API errors to console
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.data) {
      console.error('[API Error]', error.response.data)
    }
    return Promise.reject(error)
  }
)

export default apiClient

