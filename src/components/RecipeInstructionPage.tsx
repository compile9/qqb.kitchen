import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Recipe } from './types';
import axios from 'axios';
import RatingsAndReviews from './RatingsAndReviews';

const RecipeInstructionPage: React.FC = () => {
    const [recipe, setRecipe] = useState<Recipe>();
    const [tags, setTags] = useState<string[]>([]);
    const [ingredients, setIngredients] = useState<string[]>([]);
    const {recipe_id} = useParams();

    const fetchRecipeData = async () => {
        try {
            const [resRecipe, resTags, resIngredients] = await Promise.all([
                axios.get<Recipe>(`/api/recipes/${recipe_id}`),
                axios.get<string[]>(`/api/recipes/${recipe_id}/tags`),
                axios.get<string[]>(`/api/recipes/${recipe_id}/ingredients`)
            ]);
            setRecipe(resRecipe.data);
            setTags(resTags.data);
            setIngredients(resIngredients.data);
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
            <h2>{recipe?.title}</h2>
            <div className="top-display-container">
                <div className="left-side">
                    <h3>Tags</h3>
                    {tags.map((tag, tagIndex) => (
                        <p key={tagIndex} className="recipe-tags">{tag}</p>
                    ))}
                </div>
                <img src={recipe?.image} />
                <div className="right-side">
                    <h3>Ingredients</h3>
                    {ingredients.map((ingredient, ingredientIndex) => (
                        <p key={ingredientIndex} className="recipe-ingredients">{ingredient}</p>
                    ))}
                </div>
            </div>
            <div className="instruction-container">
                <p>Here is the instruction of the recipe:</p>

            </div>
            <div className="reviews-ratings">
                <RatingsAndReviews recipeId={Number(recipe_id)}/>
            </div>
        </div>
    )
}

export default RecipeInstructionPage;

//                 <ol>
//                     {recipe?.instructions.map((instruction) => (
//                         <li key={instruction.step}>
//                             <strong>Step {instruction.step}:</strong> {instruction.description}
//                         </li>
//                     ))}
//                 </ol>