import React from "react";
import { useNavigate } from "react-router-dom";
import errorImage from "../images/error.PNG";
import "../css/ErrorPage.css";
import { AlertTriangle } from "lucide-react";

export default function ErrorPage() {
    const navigate = useNavigate();

    return (
        <div className="error-page-wrapper">
            <div className="error-banner-card">
                <img src={errorImage} alt="Error" className="error-banner-image" />
                <div className="error-banner-text">
                    <AlertTriangle className="error-banner-icon" />
                    <h1>Oops!</h1>
                    <p>The page you requested was not found.</p>
                    <button className="error-home-button" onClick={() => navigate("/")}>
                        Go Back To Home
                    </button>
                </div>
            </div>
        </div>
    );
}