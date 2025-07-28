import React, {useEffect, useState} from "react";
import FeedbackForm from "./FeedbackForm";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import FeedbackListPage from "./pages/FeedbackListPage";
import './App.css';


function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/admin/feedback" element={<FeedbackListPage />} />
        </Routes>
      </Router>
  );
}

function HomePage() {
  const MAX_LENGTH = 20;
  const [name, setName] = useState("");
  const [displayedText, setDisplayedText] = useState("");
  const [loading, setLoading] = useState(false);
  const [parsedContent, setParsedContent] = useState([]);
  const remaining = MAX_LENGTH - name.length;

  useEffect(() => {
    if (!loading && displayedText) {
      setParsedContent(parseMarkdown(displayedText));
    }
  }, [loading, displayedText]);

  const handleSubmit = async () => {
    if (!name.trim()) return;

    setLoading(true);
    setDisplayedText("");

    try {
      const res = await fetch("/api/chat/name", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ name }),
      });

      if (!res.ok) {
        const errorText = await res.text();
        throw new Error(`Error ${res.status}: ${errorText}`);
      }

      const text = await res.text();
      //console.log("Response"+text);

      // Simulate typing effect
      let i = 0;
      let currentText = "";
      const interval = setInterval(() => {
        if (i < text.length) {
          currentText += text.charAt(i);
          setDisplayedText(currentText);
          i++;
        } else {
          clearInterval(interval);
          setParsedContent(parseMarkdown(text));
        }
      }, 12);

    } catch (error) {
      setDisplayedText("Failed to fetch response.");
    } finally {
      setLoading(false);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter" && !loading) {
      handleSubmit();
    }
  };

  // Convert raw text with **bold** and bullet formatting into HTML
  const parseMarkdown = (text) => {
    const lines = text.split("\n");

    const parsed = lines.map((line, idx) => {
      const isBullet = line.trim().startsWith("•");
      const cleanedLine = isBullet ? line.trim().slice(1).trim() : line.trim();
      const html = cleanedLine.replace(/\*\*(.*?)\*\*/g, "<strong>$1</strong>");

      return isBullet
          ? <li key={idx} dangerouslySetInnerHTML={{ __html: html }} />
          : <p key={idx} dangerouslySetInnerHTML={{ __html: html }} />;
    });

    // Optionally wrap bullets in <ul>
    const bullets = parsed.filter((el) => el.type === "li");
    const paragraphs = parsed.filter((el) => el.type === "p");

    return (
        <>
          {paragraphs}
          {bullets.length > 0 && <ul>{bullets}</ul>}
        </>
    );
  };


  return (
      <div className="container">
        <h1>Know Your Name</h1>

        <div className="form-group name-form">
          <label htmlFor="name">Enter Name:</label>
          <input
              id="name"
              type="text"
              placeholder="Ali"
              maxLength={MAX_LENGTH}
              value={name}
              onChange={(e) => setName(e.target.value)}
              onKeyDown={handleKeyDown}
              disabled={loading}
          />
          <small>{remaining} characters remaining</small>

          <button onClick={handleSubmit} disabled={loading || !name.trim()}>
            {loading ? <span className="spinner" /> : "Submit"}
          </button>

          <div className="output">
            {loading ? (
                <p className="response">{displayedText}</p>
            ) : (
                <div className="response">{parsedContent}</div>
            )}
          </div>
        </div>

        <div className="form-group feedback-container">
          <FeedbackForm />
        </div>
      </div>
  );
}

export default App;