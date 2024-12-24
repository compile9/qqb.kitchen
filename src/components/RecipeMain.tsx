import React, { useEffect, useState } from 'react';
import { Routes, Route, useNavigate, useLocation } from 'react-router-dom';
import dotenv from 'dotenv';
import axios from 'axios';

import RecipeList from './RecipeList';
import RecipeOfToday from './RecipeOfToday';
import RecipeInstructionPage from './RecipeInstructionPage';
import Navbar from './Navbar';
import DynamicPage from './DynamicPage';
import { Recipe, NavItem } from './types';

const RecipeMain: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();

    interface RecipeState {
        allRecipes: Recipe[];
        randomRecipes: Recipe[]
    }

    const [recipeState, setRecipeState] = useState<RecipeState>({
        allRecipes: [],
        randomRecipes: []
    });

    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    // Navbar props:
    const [navItems, setNavItems] = useState<NavItem[]>([]);
    const [selectedTags, setSelectedTags] = useState<number[]>([]);
    const [recipesByTags, setRecipesByTags] = useState<Recipe[]>([]);
    const [pathOfMultiSelection, setPathOfMultiSelection] = useState<string>("");
    const [effectTrigger, setEffectTrigger] = useState<boolean>(false);

    const fetchNavItems = async() => {
        try {
            const response = await axios.get<NavItem[]>('/api/navbarItems');
            // transform the API data to match the NavItem interface
            const transformedNavItems: NavItem[] = response.data.map(item => {
                const parentHref = `/${item.name.toLowerCase().replace(/\s+/g, '-')}`;
                return {
                    id: item.id,
                    name: item.name,
                    href: parentHref,
                    dropdownItems: item.dropdownItems?.map(dropdownItem => ({
                        id: dropdownItem.id,
                        name: dropdownItem.name,
                        href: `${parentHref}/${dropdownItem.name.toLowerCase().replace(/\s+/g, '-')}`
                    })) ?? []
                };
            });
            setNavItems(transformedNavItems);
        } catch (err) {
            setError('Failed to fetch navigation data');
        }
    }

    const toggleTagSelection = (id: number): void => {
        setSelectedTags(prevSelected =>
            prevSelected.includes(id)
                ? prevSelected.filter(item => item !== id)
                : [...prevSelected, id]
        );
        setEffectTrigger(true);
    };

    // fetch all recipes and recipes for "recipes of today" section
    const fetchRecipes = async () => {
        try {
            const responseAllRecipes = await axios.get<Recipe[]>(`/api/recipes`);
            const responseRecipesOfToDay = await axios.get<Recipe[]>(`/api/today-recipes`);
            return {
                allRecipes: responseAllRecipes.data,
                randomRecipes: responseRecipesOfToDay.data
            };
        } catch (err) {
            throw new Error((err as Error).message);
        }
    }

    const updateRecipes = async () => {
        try {
            const { allRecipes, randomRecipes } = await fetchRecipes();
            setRecipeState({
                allRecipes,
                randomRecipes
            });
        } catch (err) {
            setError((err as Error).message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        updateRecipes();
        fetchNavItems();
        // store the selected tags from the existing url after refreshing the page
        const params = new URLSearchParams(window.location.search);
        const selectedTagsParam = params.get('selected');
        if (selectedTagsParam) {
            const restoredTagsArray = selectedTagsParam.split("|").map(Number);
            setSelectedTags(restoredTagsArray);
        }
    }, []);

    useEffect(() => {
        if (effectTrigger) {
            if (effectTrigger || selectedTags) {
                // update the url after selecting/deselecting tags
                const newPath = selectedTags.length > 0 ? `tags?selected=${selectedTags.join('%7C')}` : "";
                setEffectTrigger(false);
                setPathOfMultiSelection(newPath);
                fetchRecipesByTags(newPath);
                navigate(newPath);
            }
        }
    }, [effectTrigger, selectedTags]);

    useEffect(() => {
        if (location.pathname === '/') {
            setSelectedTags([]);
        }
    }, [location.pathname]);

    const fetchRecipesByTags = async(path: string) => {
        try {
            if (path.length > 0) {
                const response = await axios.get<Recipe[]>(`/api/${path}`);
                setRecipesByTags(
                    response.data
                );
            }
        } catch (err) {
            setError('Failed to fetch recipes by selected tags');
        }
    }

    const {allRecipes, randomRecipes} = recipeState;

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;
    const newestRecipes = [...allRecipes].sort((a, b) => new Date(b.created_at).getTime() - new Date(a.created_at).getTime()).slice(0, 5);

    return (
        <div className="recipe-main">
            <Navbar navItems={navItems} selectedTags={selectedTags} setSelectedTags={setSelectedTags} toggleTagSelection={toggleTagSelection}/>
            <Routes>
                <Route path="/" element={
                    <>
                        <RecipeOfToday recipesOfToday={randomRecipes} />
                        <RecipeList title="Newest Recipes" recipes={newestRecipes} />
                    </>
                } />
                <Route path="/:pathOfMultiSelection" element={<DynamicPage selectedTags={selectedTags} recipesByTags={recipesByTags} />} />
                <Route path="/recipe/:recipe_id" element={<RecipeInstructionPage />} />
            </Routes>
        </div>
    );
};

export default RecipeMain;
