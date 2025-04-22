package com.work.library;

import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.category.CategoryEntity;
import com.work.library.infrastructure.persistance.book.BookCategoriesJpaRepository;
import com.work.library.infrastructure.persistance.book.BookJpaRepository;
import com.work.library.infrastructure.persistance.category.CategoryJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApplicationRunner implements CommandLineRunner {
    private final BookJpaRepository bookJpaRepository;

    private final CategoryJpaRepository categoryJpaRepository;

    private final BookCategoriesJpaRepository bookCategoriesJpaRepository;

    public ApplicationRunner(BookJpaRepository bookJpaRepository, CategoryJpaRepository categoryJpaRepository, BookCategoriesJpaRepository bookCategoriesJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
        this.categoryJpaRepository = categoryJpaRepository;
        this.bookCategoriesJpaRepository = bookCategoriesJpaRepository;
    }

    @Override
    public void run(String... args) {
        clearAll();
        Map<CategoryEntity, List<BookEntity>> bookMap = saveInitialCategoriesAndGetBooks();
        saveBooksAndMappings(bookMap);
    }

    private void clearAll() {
        bookCategoriesJpaRepository.deleteAll();
        bookJpaRepository.deleteAll();
        categoryJpaRepository.deleteAll();
    }

    private Map<CategoryEntity, List<BookEntity>> saveInitialCategoriesAndGetBooks() {
        CategoryEntity 문학 = categoryJpaRepository.save(new CategoryEntity("문학"));
        CategoryEntity 경제경영 = categoryJpaRepository.save(new CategoryEntity("경제경영"));
        CategoryEntity 인문학 = categoryJpaRepository.save(new CategoryEntity("인문학"));
        CategoryEntity IT = categoryJpaRepository.save(new CategoryEntity("IT"));
        CategoryEntity 과학 = categoryJpaRepository.save(new CategoryEntity("과학"));

        return new LinkedHashMap<>(Map.of(
                문학, List.of(
                        new BookEntity("너에게 해주지 못한 말들", "권태영"),
                        new BookEntity("단순하게 배부르게", "현영서"),
                        new BookEntity("게으른 사랑", "권태영")
                ),
                경제경영, List.of(
                        new BookEntity("트랜드 코리아 2322", "권태영"),
                        new BookEntity("초격자 투자", "장동혁"),
                        new BookEntity("파이어족 강환국의 하면 되지 않는다! 퀀트 투자", "홍길동")
                ),
                인문학, List.of(
                        new BookEntity("진심보다 밥", "이서연"),
                        new BookEntity("실패에 대하여 생각하지 마라", "위성원")
                ),
                IT, List.of(
                        new BookEntity("실리콘밸리 리더십 쉽다", "지승열"),
                        new BookEntity("데이터분석을 위한 A 프로그래밍", "지승열"),
                        new BookEntity("인공지능1-12", "장동혁"),
                        new BookEntity("-1년차 게임 개발", "위성원"),
                        new BookEntity("Skye가 알려주는 피부 채색의 비결", "권태영")
                ),
                과학, List.of(
                        new BookEntity("자연의 발전", "장지명"),
                        new BookEntity("코스모스 필 무렵", "이승열")
                )
        ));
    }

    private void saveBooksAndMappings(Map<CategoryEntity, List<BookEntity>> bookMap) {
        bookMap.forEach((category, books) -> books.forEach(book -> {
            BookEntity savedBook = bookJpaRepository.save(book);
            bookCategoriesJpaRepository.save(new BookCategoryMappingEntity(savedBook, category));
        }));
    }
}
