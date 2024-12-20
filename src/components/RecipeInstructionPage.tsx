import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { RecipeDetails } from './types';
import axios from 'axios';
import RatingsAndReviews from './RatingsAndReviews';

const RecipeInstructionPage: React.FC = () => {
    const [recipe, setRecipe] = useState<RecipeDetails>();
    const {recipe_id} = useParams();

    const fetchRecipeData = async () => {
        try {
            const resRecipe = await axios.get<RecipeDetails>(`/api/recipes/${recipe_id}`);
            setRecipe(resRecipe.data);
        } catch (err) {
            throw new Error((err as Error).message);
        }
    }

    useEffect(() => {
        fetchRecipeData();
    }, [recipe_id]);

    useEffect(() => {
        if (recipe) {
            console.log(recipe);
        }
    }, [recipe]);

    return (
        <div className="recipe-instruction-page">
        </div>
    )
}

export default RecipeInstructionPage;


//             <h2>{recipe?.title}</h2>
//             <div className="top-display-container">
//                 <div className="left-side">
//                     <h3>Tags</h3>
//                     {tags.map((tag, tagIndex) => (
//                         <p key={tagIndex} className="recipe-tags">{tag}</p>
//                     ))}
//                 </div>
//                 <img src={recipe?.image} />
//                 <div className="right-side">
//                     <h3>Ingredients</h3>
//                     {ingredients.map((ingredient, ingredientIndex) => (
//                         <p key={ingredientIndex} className="recipe-ingredients">{ingredient}</p>
//                     ))}
//                 </div>
//             </div>
//             <div className="instruction-container">
//                 <p>Here is the instruction of the recipe:</p>
//
//             </div>
//             <div className="reviews-ratings">
//                 <RatingsAndReviews recipeId={Number(recipe_id)}/>
//             </div>