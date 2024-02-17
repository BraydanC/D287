package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.InhousePartRepository;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.OutsourcedPartService;
import com.example.demo.service.OutsourcedPartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 *
 *
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository=outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {

       /*
        OutsourcedPart o= new OutsourcedPart();
        o.setCompanyName("Western Governors University");
        o.setName("out test");
        o.setInv(5);
        o.setPrice(20.0);
        o.setId(100L);
        outsourcedPartRepository.save(o);
        OutsourcedPart thePart=null;
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            if(part.getName().equals("out test"))thePart=part;
        }

        System.out.println(thePart.getCompanyName());
        */
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            System.out.println(part.getName()+" "+part.getCompanyName());
        }

        /*
        Product bicycle= new Product("bicycle",100.0,15);
        Product unicycle= new Product("unicycle",100.0,15);
        productRepository.save(bicycle);
        productRepository.save(unicycle);
        */

        if (partRepository.count() == 0) {

            InhousePart keys60 = new InhousePart();
            keys60.setName("keys60");
            keys60.setPrice(19.99);
            keys60.setInv(10);

            InhousePart keys65 = new InhousePart();
            keys65.setName("keys65");
            keys65.setPrice(29.99);
            keys65.setInv(10);

            InhousePart keys75 = new InhousePart();
            keys75.setName("keys75");
            keys75.setPrice(39.99);
            keys75.setInv(10);

            InhousePart frame60 = new InhousePart();
            frame60.setName("frame60");
            frame60.setPrice(19.99);
            frame60.setInv(10);

            InhousePart frame65 = new InhousePart();
            frame65.setName("frame65");
            frame65.setPrice(29.99);
            frame65.setInv(10);

            partRepository.save(keys60);
            partRepository.save(keys65);
            partRepository.save(keys75);
            partRepository.save(frame60);
            partRepository.save(frame65);
        }

        if (productRepository.count() == 0 ) {

            Product kb60 = new Product(11,"kb60", 199.99, 15);
            Product kb65 = new Product(12,"kb65", 299.99, 15);
            Product kb75 = new Product(13,"kb75", 399.99, 15);
            Product kb87 = new Product(14,"kb87", 499.99, 15);
            Product kb100 = new Product(15,"kb100", 599.99, 15);

            productRepository.save(kb60);
            productRepository.save(kb65);
            productRepository.save(kb75);
            productRepository.save(kb87);
            productRepository.save(kb100);
        }

        System.out.println("Started in Bootstrap");
        System.out.println("Number of Products"+productRepository.count());
        System.out.println(productRepository.findAll());
        System.out.println("Number of Parts"+partRepository.count());
        System.out.println(partRepository.findAll());

    }
}
