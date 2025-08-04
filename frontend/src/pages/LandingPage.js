import { useEffect, useState } from "react";
import "../App.css";
import "../css/LandingPage.css";
import {useNavigate} from "react-router-dom";

export default function LandingPage() {
    const sampleTexts = [
        `• In Arabic, Habiba means "beloved one" or "darling", and was a popular name given to slaves who were particularly favored by their owners.\n• The name Habiba has been linked to the ancient Mesopotamian goddess Ishtar, who was revered as the patron deity of love, fertility, and war.\n• In the 7th century, Habiba was the name of a revered female saint in Islam, known for her piety, wisdom, and charity. She was said to have performed miracles and was revered by both Muslims and non-Muslims.`,
        `• In the ancient Assyrian Empire, Mariam was a title given to the high priestess of the goddess Aššatu, who was believed to possess divine wisdom and prophetic powers.\n\n• The name Mariam is derived from the Hebrew name Miriam, which means "bitter" and is the name of the sister of Moses in the Bible.`,
        `• The name David is of Hebrew origin, derived from the word "dawid," which means "beloved" or "dearly loved."\n\n• The most famous biblical figure bearing the name is King David, who ruled Israel for 40 years and was said to have defeated the giant Goliath with a stone from a sling.\n\n• In ancient Greece, the name David was associated with the god Apollo, who was revered for his music and poetry.`,
        `• In Arabic, Sana means "bright" or "shining" and is the name of a cluster of stars in the constellation Scorpius.\n• Sana is also a Persian name meaning "first sight" or "first glance", which is often given to girls born at dawn or in the early hours of the day.\n• In Japanese, Sana is a unisex name meaning "three flowers" or "three blossoms", often given to children born into families with a strong cultural heritage.`,
        `• The name Susan is derived from the Old Greek name "Σοσιγene" (Sosigenē), meaning "star-born" or "woman from the moon".\n\n• In ancient Greece, the name Susan was personified as a goddess, associated with the planet Venus and the constellation Orion.\n\n• The name Susan was popularized in the Western world by the 12th-century Crusader, King Baldwin I of Jerusalem, who had a daughter named Susanna.`
    ];

    const [text, setText] = useState("");
    const [displayedText, setDisplayedText] = useState("");
    const [parsedContent, setParsedContent] = useState(null);

    useEffect(() => {
        const selected = sampleTexts[Math.floor(Math.random() * sampleTexts.length)];
        const unescaped = selected.replace(/\\n/g, "\n");
        setText(unescaped);

        // Typing effect
        let i = 0;
        let current = "";
        const interval = setInterval(() => {
            if (i < unescaped.length) {
                current += unescaped.charAt(i);
                setDisplayedText(current);
                i++;
            } else {
                clearInterval(interval);
                setParsedContent(parseMarkdown(unescaped));
            }
        }, 12);
    }, []);

    const parseMarkdown = (text) => {
        const lines = text.split("\n");

        const parsed = lines.map((line, idx) => {
            const isBullet = line.trim().startsWith("•");
            const cleaned = isBullet ? line.trim().slice(1).trim() : line.trim();
            const html = cleaned.replace(/\*\*(.*?)\*\*/g, "<strong>$1</strong>");

            return isBullet
                ? <li key={idx} dangerouslySetInnerHTML={{ __html: html }} />
                : <p key={idx} dangerouslySetInnerHTML={{ __html: html }} />;
        });

        const bullets = parsed.filter((el) => el.type === "li");
        const paragraphs = parsed.filter((el) => el.type === "p");

        return (
            <>
                {paragraphs}
                {bullets.length > 0 && <ul>{bullets}</ul>}
            </>
        );
    };
    const navigate = useNavigate();
    const isDev = process.env.REACT_APP_DEV_MODE === "true";

    const handleLogin = async () => {
        if (isDev) {
            navigate("/home");
            return;
        }
        try {
            const res = await fetch("/api/users/me", { credentials: "include" });
            if (res.ok) {
                navigate("/home");
            } else {
                window.location.href = "/oauth2/authorization/google"; // Not logged in
            }
        } catch {
            window.location.href = "/oauth2/authorization/google";
        }
    };
    return (
        <div className="full-page" style={{ padding: "2rem", fontFamily: "Tahoma, sans-serif" }}>
            <h1 style={{ textAlign: "center", fontSize: "2.5rem", marginBottom: "0.5rem" }}>
                Welcome to <span style={{ color: "#6c63ff" }}>Know Your Name</span>
            </h1>
            <p style={{ textAlign: "center", fontSize: "1.2rem", color: "#444" }}>
                Understand vital insights about your name including its meaning, cultural and religious significance
            </p>

            <div
                className="landing-layout"
            >
                <div className="info-box">
                    {parsedContent ?? displayedText}
                </div>

                <div className="login-box">
                    {isDev ? (
                        <button onClick={() => navigate("/home")}>
                            Login to Get Started (Dev Mode)
                        </button>
                    ) : (
                            <button
                                style={{
                                    padding: "12px 24px",
                                    fontSize: "16px",
                                    width: "100%",
                                    backgroundColor: "#6c63ff",
                                    color: "#fff",
                                    border: "none",
                                    borderRadius: "8px",
                                    cursor: "pointer",
                                    transition: "background-color 0.3s ease"
                                }}
                                onMouseOver={(e) => (e.target.style.backgroundColor = "#574bff")}
                                onMouseOut={(e) => (e.target.style.backgroundColor = "#6c63ff")}
                                onClick={handleLogin}
                            >
                                Login to Get Started
                            </button>
                    )}
                    <p style={{fontFamily: "cursive", marginTop: "1rem", fontSize: "0.95rem", color: "#666"}}>
                        Sign in with your Google account to continue.
                    </p>
                </div>
            </div>
        </div>
    );
}