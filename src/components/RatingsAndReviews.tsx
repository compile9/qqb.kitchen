import React from 'react';

interface RatingsAndReviewsProps {
    recipeId: number;
}

const RatingsAndReviews: React.FC<RatingsAndReviewsProps> = (props) => {
    const {recipeId} = props;
    return (
        <div className="ratings-reviews">
            <h2>Placeholder for ratings and reviews for recipe: {recipeId}</h2>
        </div>
    )
}

export default RatingsAndReviews;