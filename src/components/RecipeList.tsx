import React from 'react';
import {Recipe} from './types';
import RecipeCard from './RecipeCard';
import '../App.css';

interface RecipeListProps {
  title: string;
  recipes: Recipe[];
}

const RecipeList: React.FC<RecipeListProps> = (props) => {
  const {title, recipes} = props;
  return (
     <div className="recipe-list">
         <h2>{title}</h2>
         <div className="recipe-row">
            {recipes.map((recipe, index) => (
               <RecipeCard key={index} recipe={recipe} />
            ))}
        </div>
    </div>
  )
}


export default RecipeList;
