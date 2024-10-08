package com.sparta.spartaoutsourcing.category.service;

import com.sparta.spartaoutsourcing.category.entity.Category;
import com.sparta.spartaoutsourcing.category.repository.CategoryRepository;
import com.sparta.spartaoutsourcing.category.dto.request.CategoryRequestDto;
import com.sparta.spartaoutsourcing.category.dto.response.CategoryResponseDto;
import com.sparta.spartaoutsourcing.menu.exception.AlreadyDeletedException;
import com.sparta.spartaoutsourcing.menu.exception.AlreadyExistsException;
import com.sparta.spartaoutsourcing.menu.exception.NotFoundException;
import com.sparta.spartaoutsourcing.menu.entity.Menu;
import com.sparta.spartaoutsourcing.menu.repository.MenuRepository;
import com.sparta.spartaoutsourcing.option.entity.MenuOption;
import com.sparta.spartaoutsourcing.option.repository.MenuOptionRepository;
import com.sparta.spartaoutsourcing.optionGroup.entity.OptionGroup;
import com.sparta.spartaoutsourcing.optionGroup.repository.OptionGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j(topic = "CategoryService")
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final OptionGroupRepository optionGroupRepository;

    // 카테고리 생성
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        log.info("createCategory() 메서드 실행");

        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new AlreadyExistsException("해당하는 카테고리는 이미 존재합니다.");
        }

        Category category = new Category(categoryRequestDto.getName());

        categoryRepository.save(category);

        return new CategoryResponseDto(category);
    }

    // 카테고리 수정
    @Transactional
    public CategoryResponseDto updateCategory(Long category_id, CategoryRequestDto categoryRequestDto) {
        log.info("updateCategory() 메서드 실행");

        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new NotFoundException("해당하는 카테고리가 없습니다."));

        // 카테고리와 연관된 메뉴가 있는지 확인
        if (!category.getMenus().isEmpty()) {
            // 메뉴와 연관된 경우 새 카테고리 생성
            if (categoryRepository.existsByName(categoryRequestDto.getName())) {
                throw new AlreadyExistsException("해당하는 카테고리는 이미 존재합니다.");
            }

            // 새 카테고리 생성
            Category newCategory = new Category(categoryRequestDto.getName());
            categoryRepository.save(newCategory);

            return new CategoryResponseDto(newCategory);
        }

        // 카테고리 수정
        if (!category.getName().equals(categoryRequestDto.getName()) && categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new AlreadyExistsException("해당하는 카테고리는 이미 존재합니다.");
        }

        category.update(categoryRequestDto.getName());
        categoryRepository.save(category);

        return new CategoryResponseDto(category);
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategory(Long categoryId) {
        log.info("deleteCategory() 메서드 실행");

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("해당 카테고리를 찾을 수 없습니다."));
        
        // 해당 카테고리가 이미 삭제되었는지 확인
        if (category.getIsDeleted()) {
            throw new AlreadyDeletedException("해당하는 카테고리는 이미 삭제되었습니다.");
        }

        category.setDeleted(true);
        categoryRepository.save(category);

        // 카테고리와 연관된 메뉴 비활성화
        for (Menu menu : category.getMenus()) {
            menu.setDeleted(true);
            menuRepository.save(menu);

            // 메뉴와 연관된 옵션 그룹 및 옵션 비활성화
            for (OptionGroup optionGroup : menu.getOptionGroups()) {
                optionGroup.setDeleted(true);
                optionGroupRepository.save(optionGroup);

                for (MenuOption menuOption : optionGroup.getMenuOptions()) {
                    menuOption.setDeleted(true);
                    menuOptionRepository.save(menuOption);
                }
            }
        }
    }

    // 카테고리 복원
    @Transactional
    public void restoreCategory(Long id) {
        log.info("restoreCategory() 메서드 실행");

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 카테고리를 찾을 수 없습니다."));

        // 카테고리가 이미 복원됬는지 확인
        if (!category.getIsDeleted()) {
            throw new AlreadyDeletedException("해당하는 카테고리는 이미 복원되었습니다.");
        }

        category.setDeleted(false);
        categoryRepository.save(category);


        // 메뉴와 옵션 그룹과 옵션 복원
        for (var menus : category.getMenus()) {
            menus.setDeleted(false);
            menuRepository.save(menus);

            for (OptionGroup optionGroup : menus.getOptionGroups()) {
                optionGroup.setDeleted(false);
                optionGroupRepository.save(optionGroup); // 옵션 그룹 복원

                for (var menuOption : optionGroup.getMenuOptions()) {
                    menuOption.setDeleted(false);
                    menuOptionRepository.save(menuOption); // 메뉴 옵션 복원
                }
            }
        }
    }

    // 활성화된 모든 카테고리 조회
    public List<CategoryResponseDto> getAllActiveCategories() {
        log.info("getAllActiveCategories() 메서드 실행");

        // 카테고리들 중에서 활성화 상태인 카테고리들을 반환
        List<Category> activeCategories = categoryRepository.findByIsDeleted(false);
        return activeCategories.stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }
}
