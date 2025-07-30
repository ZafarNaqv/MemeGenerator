// components/HoverFlipCard.jsx
import React from "react";
import "../css/FlipCard.css";

const HoverFlipCard = ({ image, name, meaning, origin, historical }) => (
    <div className="flip-card">
        <div className="flip-card-inner hover">
            <div className="flip-card-front">
                <img src={image} alt={name} />
                <h2>{name}</h2>
            </div>
            <div className="flip-card-back">
                <h3>Meaning:</h3>
                <p>{meaning}</p>
                <h3>Origin:</h3>
                <p>{origin}</p>
                <h3>Historical Significance:</h3>
                <p>{historical}</p>
            </div>
        </div>
    </div>
);

export default HoverFlipCard;