import React from 'react';
import {Recipe} from './types';
import { Link } from 'react-router-dom';

const RecipeCard: React.FC<{recipe: Recipe}> = ({recipe}) => (
    <div key={recipe.id} className="recipe-card">
        <Link to={`/recipe/${encodeURIComponent(recipe.id)}`}>
          <img
            src={recipe.image}
            className="recipe-image"
          />
          <div className="recipe-title">{recipe.title}</div>
        </Link>
    </div>
)

export default RecipeCard;