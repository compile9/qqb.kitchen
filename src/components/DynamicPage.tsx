import React from 'react';
import RecipeList from './RecipeList';
import { Recipe } from './types';

interface DynamicPageProps {
    selectedTags: number[];
    recipesByTags: Recipe[];
}

const DynamicPage: React.FC<DynamicPageProps> = (props) => {
    const {selectedTags, recipesByTags} = props;
    return (
        <div>
            <h1>Dynamic Page Placeholder: {selectedTags.toString()}</h1>
            <RecipeList title="recipes by tags: " recipes={recipesByTags} />
        </div>
    );
};

export default DynamicPage;
