import React from 'react';
import {Recipe} from './types';
import { Link } from 'react-router-dom';

const RecipeCard: React.FC<{recipe: Recipe}> = ({recipe}) => (
    <div key={recipe.id} className="recipe-card">
        <Link to={`/recipe/${encodeURIComponent(recipe.id)}`}>
          <img
            src={recipe.image}
            alt={recipe.title}
            className="recipe-image"
          />
        </Link>
    </div>
)

export default RecipeCard;