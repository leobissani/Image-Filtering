package br.com.filters.Java;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
*@author Leonardo Bissani
*/

public class Filtering {

	public static void main(String[] args) {

		int option = 0;
		
		try {
			BufferedImage image = ImageIO.read(new File("images/image.jpg"));
			Filters filter = new Filters();
			Scanner read = new Scanner(System.in);
			
			System.out.println("What do you want to do with the image?");
			System.out.println("1 - Negative");
			System.out.println("2 - GrayScale");
			System.out.println("3 - Threshold");
			System.out.println("4 - 3x3 Median Filter");
			System.out.println("5 - Insert noise");
			System.out.println("6 - Sepia");
			System.out.println("7 - KMeans");
			System.out.println("0 - Exit");
			System.out.print("Option: ");
			option = read.nextInt();
			
			switch(option) {
				case 1:																	
					filter.negativeFilter(image);	
					ImageIO.write(image, "jpg", new File("images/imageNegative.jpg"));
					System.out.println("\nNegative image generated successfully!");
					break;
					
				case 2:
					filter.grayscaleFilter(image);
					ImageIO.write(image, "jpg", new File("images/imageGrayscale.jpg"));
					System.out.println("\nGrayscale image generated successfully!");
					break;
					
				case 3:
					int threshold = 0;
					System.out.println("Insert below a value to the threshold (from 0 to 255).");
					System.out.print("Threshold value: ");
					threshold = read.nextInt();
					filter.thresholdFilter(image, threshold);
					ImageIO.write(image, "jpg", new File("images/imageThreshold.jpg"));
					System.out.println("\nThreshold image generated successfully with value of " + threshold + "!");
					break;
					
				case 4:
					filter.medianFilter(image);
					ImageIO.write(image, "jpg", new File("images/imageMedian.jpg"));
					System.out.println("\nMedian image generated successfully with mask 3x3!");
					break;
				case 5:
					filter.addNoise(image);
					ImageIO.write(image, "jpg", new File("images/imageNoise.jpg"));
					System.out.println("\nNoisy image generated successfully!");
					break;
				case 6:
					filter.sepiaFilter(image);
					ImageIO.write(image, "jpg", new File("images/imageSepia.jpg"));
					System.out.println("\nSepia image generated successfully!");
					break;
				case 7:
					int k = 0;
					System.out.println("Insert below a value K to the kmeans algorithm.");
					System.out.print("K value: ");
					k = read.nextInt();
					filter.kMeans(image, k);
					ImageIO.write(image, "jpg", new File("images/imageKMeans.jpg"));
					System.out.println("\nKMeans image generated successfully with a K of " + k + "!");
					break;
				default: 
					System.out.println("\nNot an available option. Try again!");
					break;
			}
			
			if(read != null)
				read.close();
		}
		
		catch (IOException e) {
			System.out.println("Error! File not found!\n");			
		}
		
		catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

}
