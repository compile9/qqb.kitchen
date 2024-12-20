export interface Recipe {
  id: number;
  title: string;
  description: string;
  rating: number;
  image: string;
  duration: number;
  created_at: string
}

export interface RecipeDetails extends Recipe {
  tags: string[];
  instructions: string[];
  ingredients: string[]
}

export interface DropdownItem {
    id: number;
    name: string;
    href: string
}

export interface NavItem {
    id: number;
    name: string;
    href: string;
    dropdownItems?: DropdownItem[]
}