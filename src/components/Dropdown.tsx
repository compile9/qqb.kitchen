import React, { useState, useEffect } from 'react';
import { NavItem } from './types';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import { Recipe } from './types';

interface DropdownProps {
    navItem: NavItem;
    activeDropdown: number | null;
    selectedTags: number[];
    setSelectedTags: React.Dispatch<React.SetStateAction<number[]>>;
    toggleTagSelection: (id: number) => void
}

const Dropdown: React.FC<DropdownProps> = (props) => {
    const { navItem, activeDropdown, selectedTags, setSelectedTags, toggleTagSelection } = props;
    const navigate = useNavigate();
    const [hoveredItem, setHoveredItem] = useState<number | null>(null);

    const handleMouseEnter = (dropdownId: number): void => {
        setHoveredItem(dropdownId);
    };

    const handleMouseLeave = (): void => {
        setHoveredItem(null);
    }

    return (
        <div>
            <Link to={navItem.href}>{navItem.name}</Link>
            {activeDropdown === navItem.id && navItem.dropdownItems && navItem.dropdownItems.length > 0 && (
                <ul className="dropdown">
                    {navItem.dropdownItems.map(dropdownItem => {
                        const isHovered = hoveredItem === dropdownItem.id;
                        const hoverStyle = isHovered ? {backgroundColor: '#B67233'} : {};
                        const isSelected = selectedTags.includes(dropdownItem.id);
                        const selectedStyle = isSelected ? {backgroundColor: '#ffa6c9', color: 'black'} : {};
                        return (
                            <li key={dropdownItem.id} onMouseEnter={() => handleMouseEnter(dropdownItem.id)} onMouseLeave={() => handleMouseLeave()} style={hoverStyle}>
                                <a style={selectedStyle} onClick={() => toggleTagSelection(dropdownItem.id)}>
                                    <span>{dropdownItem.name}</span>
                                </a>
                            </li>
                        );
                    })}
                </ul>
            )}
        </div>);
};

export default Dropdown;