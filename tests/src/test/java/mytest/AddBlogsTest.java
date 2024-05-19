package mytest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.Files;

public class AddBlogsTest extends BasePage {
    private String readFileToString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
    }

    @Test
    public void testAddBlog() throws InterruptedException, IOException {
        // Print current working directory
        System.out.println("Current working directory: " + new File(".").getAbsolutePath());

        this.driver.get("https://erat-travel.com/login");
        authenticate(username, password);
        Thread.sleep(3000);

        WebElement blogPage = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Blog")));
        blogPage.click();
        String bgColor = blogPage.getCssValue("background-color");
        assertTrue("Background color is white!", !bgColor.equals("rgba(42, 61, 59, 1)"));

        WebElement newBlogPage = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add Blog")));
        newBlogPage.click();

        // Fill in title
        WebElement title = wait.until(ExpectedConditions.elementToBeClickable(By.name("title")));
        title.sendKeys("My First Blog");

        // Description
        WebElement description = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description")));
        description.sendKeys("This is my first blog post.");

        // news path
        WebElement newsPath = wait.until(ExpectedConditions.elementToBeClickable(By.name("href")));
        newsPath.sendKeys("/first-blog");

        // select category
        selectDropdownOption(By.id("react-select-2-input"), By.id("react-select-2-listbox"), "div[class*='option']:first-child", false);

        // select tags
        selectDropdownOption(By.id("react-select-3-input"), By.id("react-select-3-listbox"), "div[class*='option']:first-child", false);

        // MDX data to fill Text area
        String mdxData = readFileToString("src/test/resources/mdx/blog.mdx");
        WebElement mdxEditor = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));
        mdxEditor.sendKeys(mdxData);

        // Drag and drop file upload
        WebElement dropzone = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/section/form/div[4]/div[2]/div")));
        
        // Check if the file exists
        String imagePath = "/home/selenium/tests/src/test/resources/images/blog.jpeg";
        File imageFile = new File(imagePath);

        System.out.println("Image file path: " + imageFile.getAbsolutePath());
        System.out.println("Image file exists: " + imageFile.exists());
        
        if (!imageFile.exists()) {
            throw new RuntimeException("File not found test 1: " + imageFile.getAbsolutePath());
        }
        
        dragAndDropFile(dropzone, imageFile);

        // Submit form
        // It can't be submit if there is no image
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        submitButton.click();
        // so if the image is not found, submission will fail and not redirected to the blog list page
        // The new blogs will not be visible in the blog list page because it need verification from another role
        Thread.sleep(2500);
        String currentUrl = driver.getCurrentUrl();
        boolean isOnBlogPage = currentUrl.equals("https://erat-travel.com/blog");
        assertTrue("Not on the blog page!", isOnBlogPage);
    }

    private void dragAndDropFile(WebElement dropzone, File file) throws InterruptedException, IOException {
        
        String jsDragAndDrop = "var dropTarget = arguments[0];"
            + "var fileInput = document.createElement('input');"
            + "fileInput.type = 'file';"
            + "fileInput.style.display = 'none';"
            + "document.body.appendChild(fileInput);"
            + "fileInput.files = new DataTransfer().files;"
            + "var event = new Event('drop', { 'bubbles': true });"
            + "var dataTransfer = { files: fileInput.files };"
            + "event.dataTransfer = dataTransfer;"
            + "dropTarget.dispatchEvent(event);"
            + "document.body.removeChild(fileInput);";
        
        js.executeScript(jsDragAndDrop, dropzone);
        
        Thread.sleep(2000);
    }
}
