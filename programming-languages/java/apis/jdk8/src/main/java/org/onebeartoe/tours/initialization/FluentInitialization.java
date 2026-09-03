
package org.onebeartoe.tours.initialization;

/**
 *
 */
public class FluentInitialization 
{
    public static class FluentStackPane
    {
        // 1. Make fields final for immutability
        final String style;
        final int width;
        final int height;
       
    // 2. Private constructor forces the use of the Builder
    private FluentStackPane(Builder builder) 
    {
        this.style = builder.style;
        this.width = builder.width;
        this.height = builder.height;
    }

    // 3. Static inner Builder class
    public static class Builder 
    {
        String style;
        int width;
        int height;
        

        // Setter-like methods that return 'this' for chaining
        public Builder style(String firstName) {
            this.style = firstName;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }



        // The final step that instantiates the target object
        public FluentStackPane build() {
            // Optional: Add validation logic here before creating the object
            return new FluentStackPane(this);
        }
    }

        // Getters (No setters to maintain immutability)
        public String getStyle() { return style; }
        public int getWidth() { return width; }
        public int getAge() { return height; } 
    }
}
