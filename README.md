"# MemeGenerator" 
## TODO:
- Add backend apis
- Implement Advice to log parameter details
- Add google Signin maybe?
- Where to store images
- Logging framework
- Cloud deployment
- Add a way to save feedback
- On production might have to include secrets manager to include api key
- API:Get all generated images, need to do maybe pagination


## 🧠 Core Features Recap
🖼️ Frontend
- Upload a selfie (with loader animation during generation).

- Fetch available prompts (from backend).

- View previously generated comics.

- Hosted for free (e.g., Vercel for frontend).

## 🧰 Backend (Java Spring Boot)
Endpoint to:

- Accept image upload.

- Validate or resize image (max 1MB).

- Read prompt from a JSON file (based on user selection).

- Call ChatGPT with image + prompt.

- Save result (image/base64).

- Secure using API key/token.