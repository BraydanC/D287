## WESTERN GOVERNORS UNIVERSITY
## D287 – JAVA FRAMEWORKS
## Braydan Coffing
## Practical Assessment
## README.md - Used to track changes made to project, changes include line changes, where changes were made, and what changes were completed
## BASIC INSTRUCTIONS
For this project, you will use the Integrated Development Environment (IDE) link in the web links section of this assessment to install the IDE, IntelliJ IDEA (Ultimate Edition). All relevant links are on the course page. Please refer to the course of study for specific links. You will sign up for a free student license using your WGU.edu email address. Please see the “IntelliJ Ultimate Edition Instructions” attachment for instructions on how do this. Next you will download the “Inventory Management Application Template Code” provided in the web links section and open it in IntelliJ IDEA (Ultimate Edition). You will upload this project to a private external GitLab repository and backup regularly. As a part of this, you have been provided with a base code (starting point).

A. Create your subgroup and project by logging into GitLab using the web link provided and using the “GitLab How-To” web link, and do the following:
•  Clone the project to the IDE.
•  Commit with a message and push when you complete each of the tasks listed below (e.g., parts C to J).
Note: You may commit and push whenever you want to back up your changes, even if a task is not complete.
•  Submit a copy of the Git repository URL and a copy of the repository branch history retrieved from your repository, which must include the commit messages and dates.
Note: Wait until you have completed all the following prompts before you create your copy of the repository branch history.
- This is complete

B. Create a README file that includes notes describing where in the code to find the changes you made for each of parts C to J. Each note should include the prompt, file name, line number, and change.
- Complete. You're reading it! I am using what is available to me and modifying the original README.md that was already generated when the project was built from GitLab.

C. Customize the HTML user interface for your customer’s application. The user interface should include the shop name, the product names, and the names of the parts.
Note: Do not remove any elements that were included in the screen. You may add any additional elements you would like or any images, colors, and styles, although it is not required.
<pre>
Changed - mainscreen.html
    Line 14 - < title > My Bicycle Shop < /title >
    Changed to:
    Line 14 - < title > Braydan's Keyboard Store < /title >

    Line 19 - < h1 > Shop < /h1 >
    Changed to:
    Line 19 - < h1 > Shop for Keyboards < /h1 >
</pre>

D.  Add an “About” page to the application to describe your chosen customer’s company to web viewers and include navigation to and from the “About” page and the main screen.
<pre>
    CREATED - about.html

        < !DOCTYPE html >  
        < html lang="en" >  
        < head >  
            < meta charset="UTF-8" >  
            < title >About Us< /title >  
        < /head >  
        < body >  
        
            < p >  
                Braydan's Keyboard Store is your favorite online shop for building custom keyboards. If you don't like it, 100% refundable no matter what.
                We are a young company outside of Madison, Wisconsin. Please come see us if you are in town! 5% off while shopping in store near the Noodles & Co on State Street.
            < /p >  
        
            < a href="http://localhost:8080/" >  Link to Main Screen < /a >  
        < /body >  
        < /html >  

    INSERT - mainscreen.html
    
        Line 89 - < a th:href="@{about}" > Click here to learn about us! < /a >

    INSERT - MainScreenControllerr, LINES 55-58
        @GetMapping("/about")
        public String about() {
            return "about"; // about.html page reference
        } 
</pre>

E.  Add a sample inventory appropriate for your chosen store to the application. You should have five parts and five products in your sample inventory and should not overwrite existing data in the database.
Note: Make sure the sample inventory is added only when both the part and product lists are empty. When adding the sample inventory appropriate for the store, the inventory is stored in a set so duplicate items cannot be added to your products.
When duplicate items are added, make a “multi-pack” part.
<pre>
INSERT - BootStrapData.java, lines 74-121

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
</pre>

F.  Add a “Buy Now” button to your product list. Your “Buy Now” button must meet each of the following parameters:
•  The “Buy Now” button must be next to the buttons that update and delete products.
•  The button should decrement the inventory of that product by one. It should not affect the inventory of any of the associated parts.
•  Display a message that indicates the success or failure of a purchase.

<pre>
INSERT - mainscreen.html Line 85
Added "Buy Now" button to the products list. I mapped the button to invoke the BuyProductController (see below)

< a th:href="@{/buyProduct(productId=${tempProduct.id})}" class="btn btn-primary btn-sm mb-3">Buy Now < /a >

CREATE - BuyProductController.java All lines
Created the controller and mapped to /buyProduct with the @GetMapping option.
It first checks to ensure it exists, if so then it checks to ensure product availability, and if it's available, then decrements the inventory by one and returns the purchaseSuccessful page.
If those checks do not pass, then it returns the outOfStock page.

@Controller
public class BuyProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/buyProduct")
    public String buyProduct(@RequestParam("productId") Long productId, Model theModel) {

        Optional<Product> purchaseProd = productRepository.findById(productId);

        if (purchaseProd.isPresent()) {
            Product product = purchaseProd.get();

            if (product.getInv() > 0) {
                product.setInv(product.getInv() - 1);
                productRepository.save(product);
                return "purchaseSuccessful";
            } else {
                return "outOfStock"; 
            }
        } else {
            return "outOfStock";
        }
    }
}

CREATE - purchaseSuccessful.html All lines
Created a simple HTML page to show that the purchase was successful

< !DOCTYPE html >
< html lang="en" >
< head >
    < meta charset="UTF-8" >
    < title >Purchase Successful!< /title >

    < link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous" >

< /head >
< body >

< h1 class="text-center">Your Purchase was successful!!!< /h1 >

 < a th:href="@{/mainscreen}" class="btn">Back to Main< /a >

< /body >
< /html >

CREATE - outOfStock.html All lines
Created outOfStock page to display if product was out of stock

< !DOCTYPE html >
< html lang="en" >
< head >
    < meta charset="UTF-8" >
    < title >Out Of Stock!< /title >

    < link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous" >

< /head >
< body >

< h1 class="text-center">This product is out of stock!!!< /h1 >

< a th:href="@{/mainscreen}" class="btn">Back to Main< /a >

< /body >
< /html >
</pre>

G.  Modify the parts to track maximum and minimum inventory by doing the following:

•  Add additional fields to the part entity for maximum and minimum inventory.
<pre>
    INSERT - mainscreen.html, LINES 38-39 AND LINES 48-49
        < th > Minimum < /th > 
        < th > Maximum < /th > 

        < td th:text="${tempPart.minimum}" > 1 < /td > 
        < td th:text="${tempPart.maximum}" > 1 < /td >
</pre>

•  Modify the sample inventory to include the maximum and minimum fields.
<pre>
    INSERT - Part.java, LINES 36-38 AND LINES 109-113
        @Min (value = 0, message = "Minimum inventory must be > 0")
        int minimum;
        int maximum;

        public void setMinimum(int minimum) { this.minimum = minimum; }
        public int getMinimum() { return this.minimum; }
         
        public void setMaximum(int maximum) { this.maximum = maximum; }
        public int getMaximum() { return this.maximum; }

    INSERT - InhousePart.java AND OutsourcedPart.java, LINES 18-19
        this.minimum = 0;
        this.maximum = 100;
</pre>

•  Add to the InhousePartForm and OutsourcedPartForm forms additional text inputs for the inventory so the user can set the maximum and minimum values.
<pre>
    INSERT - InhousePartForm.html AND OutsourcedPartForm.html, LINES 24-34

        < p >  < input type="text" th:field="*{minimum}" placeholder="Minimum" class="form-control mb-4 col-4"/ >  < /p > 
             
        < p >  < input type="text" th:field="*{maximum}" placeholder="Maximum" class="form-control mb-4 col-4"/ >  < /p > 
             
        < p >  < input type="text" th:field="*{partId}" placeholder="Part ID" class="form-control mb-4 col-4"/ >  < /p > 
    
        < p > 
        < div th:if="${#fields.hasAnyErrors()}" > 
            < ul >  < li th:each="err: ${#fields.allErrors()}" th:text="${err}" >  < /li >  < /ul > 
        < /div > 
        < /p > 

    INSERT - OutsourcedPartForm.html, LINES 25-29

    < p > < input type="text" th:field="*{minimum}" placeholder="Minimum" class="form-control mb-4 col-4" / > < /p >
    < p th:if="${#fields.hasErrors('inv')}" th:errors="*{inv}">Inventory Error < / p >

    < p > < input type="text" th:field="*{maximum}" placeholder="Maximum" class="form-control mb-4 col-4" / > < /p >
    < p th:if="${#fields.hasErrors('inv')}" th:errors="*{inv}">Inventory Error< /p >
</pre>

•  Rename the file the persistent storage is saved to.
<pre>
    CHANGE - application.properties
        spring.datasource.url=jdbc:h2:file:~/spring-boot-h2-db102
        TO
        spring.datasource.url=jdbc:h2:file:~/src/main/resources/spring-boot-h2-db102
</pre>

•  Modify the code to enforce that the inventory is between or at the minimum and maximum value.
<pre>

    INSERT - Part.java LINES 93-99
        public void validateLimits() {
            if (this.inv < this.minimum) {
                this.inv = this.minimum;
            } else if (this.inv > this.maximum ) {
                this.inv = this.maximum;
            }
        }


    INSERT - InhousePartServiceImpl.java(LINE 54) AND OutsourcedPartServiceImpl.java(LINE 52)
        save()...etc
        thePart.validateLimits();

The validateLimits function is set to confirm that the inventory levels are within the acceptable range (min/max) set in previous parts.
</pre>

H.  Add validation for between or at the maximum and minimum fields. The validation must include the following:
<pre>
INSERT - Part.java, Lines 21-22
@ValidatePartInv
@ValidatePartInvMin
</pre>

• Display error messages for low inventory when adding and updating parts if the inventory is less than the minimum number of parts. 
<pre>
CREATE - PartInventoryMinimumValidator.java All lines

        package com.example.demo.validators;
        
        import com.example.demo.domain.Part;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.ApplicationContext;
        
        import javax.validation.ConstraintValidator;
        import javax.validation.ConstraintValidatorContext;
        
        /**
         *
         *
         *
         *
         */
        public class PartInventoryMinimumValidator implements ConstraintValidator < ValidPartInventoryMinimum, Part > {
            @Autowired
            private ApplicationContext context;
        
            public static  ApplicationContext myContext;
        
            @Override
            public void initialize(ValidPartInventoryMinimum constraintAnnotation) {
                ConstraintValidator.super.initialize(constraintAnnotation);
            }
        
            @Override
            public boolean isValid(Part part, ConstraintValidatorContext constraintValidatorContext) {
                return part.getInv() > part.getMinimum();
            }
        }

CREATE - ValidPartInventoryMinimum.java All Lines
        package com.example.demo.validators;
        
        import javax.validation.Constraint;
        import javax.validation.Payload;
        import java.lang.annotation.ElementType;
        import java.lang.annotation.Retention;
        import java.lang.annotation.RetentionPolicy;
        import java.lang.annotation.Target;
        
        /**
         *
         *
         *
         *
         */
        @Constraint(validatedBy = {PartInventoryMinimumValidator.class})
        @Target({ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface ValidPartInventoryMinimum {
            String message() default "Inventory cannot be lower than required minimum";
            Class [] groups() default {};
            Class [] payload() default {};
        }
</pre>

• Display error messages for low inventory when adding and updating products lowers the part inventory below the minimum. 
<pre>
NOTE: I did not create a validator here due to requirements of Step F. Those requirements are that the "Buy Now" button
does not change the inventory of associated parts. 

If I did implement this feature, the buyProduct() function would require modification as such:
    public boolean buyProduct() {
        if (this.inv >= 1 ) {
            this.inv--;

            for(Part part:this.getParts()){
                if (part.getInv() >= 1) {
                    part.setInv(part.getInv() - 1);
                } else {
                    return false;
                }
            }
    }

I would have then edited the BuyPartError.html page to reflect the validation error that would have been triggered by 
the part inventory min validator function.
</pre>

• Display error messages when adding and updating parts if the inventory is greater than the maximum.
<pre>
    CREATE - PartInventoryValidator.java All Lines

        package com.example.demo.validators;
        
        import com.example.demo.domain.Part;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.ApplicationContext;
        
        import javax.validation.ConstraintValidator;
        import javax.validation.ConstraintValidatorContext;
        
        /**
         *
         *
         *
         *
         */
        public class PartInventoryValidator implements ConstraintValidator < ValidPartInventory, Part > {
            @Autowired
            private ApplicationContext context;
        
            public static  ApplicationContext myContext;
        
            @Override
            public void initialize(ValidPartInventory constraintAnnotation) {
                ConstraintValidator.super.initialize(constraintAnnotation);
            }
        
            @Override
            public boolean isValid(Part part, ConstraintValidatorContext constraintValidatorContext) {
                return part.getInv() <= part.getMaximum();
            }
        }

    CREATE - ValidPartInventory.java All Lines

        package com.example.demo.validators;
        
        import javax.validation.Constraint;
        import javax.validation.Payload;
        import java.lang.annotation.ElementType;
        import java.lang.annotation.Retention;
        import java.lang.annotation.RetentionPolicy;
        import java.lang.annotation.Target;
        
        /**
         *
         *
         *
         *
         */
        @Constraint(validatedBy = {PartInventoryValidator.class})
        @Target({ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface ValidPartInventory {
            String message() default "Inventory cannot exceed maximum value";
            Class [] groups() default {};
            Class [] payload() default {};
        }
</pre>

I. Add at least two unit tests for the maximum and minimum fields to the PartTest class in the test package. 
<pre>
    INSERT - PartTest.java, LINES 160-177
        @Test
        void getMinimum() {
            int minimum=5;
            partIn.setMinimum(minimum);
            assertEquals(minimum, partIn.getMinimum());
            partOut.setMinimum(minimum);
            assertEquals(minimum, partOut.getMinimum());
        }
    
        @Test
        void getMaximum() {
            int maximum=5;
            partIn.setMaximum(maximum);
            assertEquals(maximum, partIn.getMaximum());
            partOut.setMaximum(maximum);
            assertEquals(maximum, partOut.getMaximum());
        }
</pre>

J. Remove the class files for any unused validators in order to clean your code. 
<pre>
Upon review of the following validators:
- DeletePartValidator - DELETED, NOT USED
- EnufPartsValidator - 1 Usage, not deleted
- PartInventoryMinimumValidator - 1 Usage, not deleted
- PartInventoryValidator - 1 Usage, not deleted
- PriceProductValidator - 3 Usages total, not deleted
- ValidDeletePart - 2 Usages, not deleted
- ValidEnufParts - 4 Usages, not deleted
- ValidPartInventoryMaximum - 4 Usages, not deleted
- ValidPartInventoryMinimum - 4 Usages, not deleted
- ValidProductPrice - 4 Usages, not deleted

Deleted DeletePartValidator as it was not being used.
</pre>