package ru.geekbrains.persist.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.geekbrains.persist.model.Product;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findAllForBackendTest() {
        TypedQuery<Product> query = entityManager.getEntityManager()
                .createQuery("select new Product(p.id, p.name, " +
                        "p.cost, p.shortDescription, mp.picture.id) from Product p " +
                        "left join p.mainPicture mp where p.id <= 10 order by p.id asc", Product.class);
        List<Product> expectedProducts = query.getResultList();

        Sort byId = Sort.by("id");
        Pageable pageable = PageRequest.of(0, 10, byId);
        Page<Product> expectedPage = new PageImpl<>(expectedProducts, pageable, 30);
        Specification<Product> specification = Specification.where(null);
        Page<Product> resultPage = productRepository.findAllForBackend(specification, pageable);

        assertEquals(expectedPage.getTotalElements(), resultPage.getTotalElements());
        List<Product> resultProducts = resultPage.getContent();
        assertEquals(expectedProducts.size(), resultProducts.size());
        assertEqualsProducts(expectedProducts, resultProducts);
    }

    @Test
    public void findAllForAdminTest() {
        TypedQuery<Product> query = entityManager.getEntityManager()
                .createQuery("select new Product(p.id, p.name, " +
                        "p.cost, mp.picture.id) from Product p " +
                        "inner join p.mainPicture mp where p.id <= 10 order by p.id asc", Product.class);
        List<Product> expectedProducts = query.getResultList();

        Sort byId = Sort.by("id");
        Pageable pageable = PageRequest.of(0, 10, byId);
        Page<Product> expectedPage = new PageImpl<>(expectedProducts, pageable, 30);
        Specification<Product> specification = Specification.where(null);
        Page<Product> resultPage = productRepository.findAllForAdmin(specification, pageable);

        assertEquals(expectedPage.getTotalElements(), resultPage.getTotalElements());
        List<Product> resultProducts = resultPage.getContent();
        assertEquals(expectedProducts.size(), resultProducts.size());
        assertEqualsProducts(expectedProducts, resultProducts);
    }

    @Test
    public void findByIdForCartTest() {
        TypedQuery<Product> query = entityManager.getEntityManager()
                .createQuery("select new Product(p.id, p.name, " +
                        "p.cost) from Product p " +
                        "where p.id = 1", Product.class);
        Product expectedProduct = query.getSingleResult();

        Product resultProduct = productRepository.findByIdForCart(1L).orElseThrow();

        assertEquals(expectedProduct.getId(), resultProduct.getId());
        assertEquals(expectedProduct.getName(), resultProduct.getName());
        assertEquals(expectedProduct.getCost(), resultProduct.getCost());
    }

    @Test
    public void testFindAllByIdIn() {
        TypedQuery<Product> query = entityManager.getEntityManager()
                .createQuery("select new Product(p.id, p.name, p.cost) from Product p where p.id in (1, 2, 3)",
                        Product.class);
        List<Product> expectedProducts = query.getResultList();

        List<Product> resultProducts = productRepository.findAllByIdIn(List.of(1L, 2L, 3L));

        assertEquals(expectedProducts.size(), resultProducts.size());
        for(int i = 0; i < expectedProducts.size(); i++) {
            Product expectedProduct = expectedProducts.get(i);
            Product resultProduct = resultProducts.get(i);
            assertEquals(expectedProduct.getId(), resultProduct.getId());
            assertEquals(expectedProduct.getName(), resultProduct.getName());
            assertEquals(expectedProduct.getCost(), resultProduct.getCost());
        }
    }

    private void assertEqualsProducts(List<? extends Product> expectedProducts, List<Product> resultProducts) {
        for(int i = 0; i < expectedProducts.size(); i++) {
            Product expectedProduct = expectedProducts.get(i);
            Product resultProduct = resultProducts.get(i);
            assertEquals(expectedProduct.getId(), resultProduct.getId());
            assertEquals(expectedProduct.getName(), resultProduct.getName());
            assertEquals(expectedProduct.getCost(), resultProduct.getCost());
            assertEquals(expectedProduct.getShortDescription(), resultProduct.getShortDescription());
            assertEquals(expectedProduct.getMainPicture().getId(), resultProduct.getMainPicture().getId());
            assertEquals(expectedProduct.getDescription(), resultProduct.getDescription());
        }
    }

}
