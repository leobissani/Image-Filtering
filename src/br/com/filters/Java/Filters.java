package br.com.filters.Java;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Filters {
	
	public BufferedImage negativeFilter(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				int r = 255 - (int)((rgb & 0x00FF0000) >>> 16);
				int g = 255 - (int)((rgb & 0x0000FF00) >>> 8);
				int b = 255 - (int)(rgb & 0x000000FF);
				
				Color newRgb = new Color(r, g, b);
				
				image.setRGB(i, j, newRgb.getRGB());
			}
		}
		
		return image;
	}
	
	public BufferedImage grayscaleFilter(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int mean = 0;
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int rgb = image.getRGB(i,j);
				int r = (int)((rgb & 0x00FF0000) >>> 16);
				int g = (int)((rgb & 0x0000FF00) >>> 8);
				int b = (int)(rgb & 0x000000FF);
				
				mean = ((r + g + b)/3);
				
				Color newRgb = new Color(mean, mean, mean);
				image.setRGB(i, j, newRgb.getRGB());
			}
		}
		
		return image;
	}
	
	public BufferedImage thresholdFilter(BufferedImage image, int threshold) {
		int width = image.getWidth();
		int height = image.getHeight();
		int mean = 0;
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int rgb = image.getRGB(i,j);
				int r = (int)((rgb & 0x00FF0000) >>> 16);
				int g = (int)((rgb & 0x0000FF00) >>> 8);
				int b = (int)(rgb & 0x000000FF);
				
				mean = ((r + g + b)/3);
				
				Color white = new Color(255, 255, 255);
				Color black = new Color(0, 0, 0);
				
				if(mean > threshold)
					image.setRGB(i, j, white.getRGB());
				else 
					image.setRGB(i, j, black.getRGB());
			}
		}
		
		return image;
	}
	
	public BufferedImage medianFilter(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		Color[] pixel = new Color[9];
		int[] r = new int[9];
		int[] g = new int[9];
		int[] b = new int[9];

		for(int i = 1; i < width-1; i++) {
			for(int j = 1; j < height-1; j++) {
				pixel[0] = new Color(image.getRGB(i-1, j-1));
				pixel[1] = new Color(image.getRGB(i-1, j));
				pixel[2] = new Color(image.getRGB(i-1, j+1));
				pixel[3] = new Color(image.getRGB(i, j-1));
				pixel[4] = new Color(image.getRGB(i, j));
				pixel[5] = new Color(image.getRGB(i, j+1));
				pixel[6] = new Color(image.getRGB(i+1, j-1));
				pixel[7] = new Color(image.getRGB(i+1, j));
				pixel[8] = new Color(image.getRGB(i+1, j+1));
				
				for(int k = 0; k < 9; k++) {
					r[k] = pixel[k].getRed();
					g[k] = pixel[k].getGreen();
					b[k] = pixel[k].getBlue();
				}
				
				Arrays.sort(r);
				Arrays.sort(g);
				Arrays.sort(b);
				
				image.setRGB(i, j, new Color(r[4], g[4], b[4]).getRGB());
			}
		}
		
		return image;
	}
	
	public BufferedImage addNoise(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int randomNumber = ThreadLocalRandom.current().nextInt(1, 25);
				
				if(randomNumber == 1) {
					int a = (int)(Math.random() * 256);
					int r = (int)(Math.random() * 256);
					int g = (int)(Math.random() * 256);
					int b = (int)(Math.random() * 256);
					
					int pixel = (a << 24) | (r << 16) | (g << 8) | (b);
					
					image.setRGB(i, j, pixel);
				}
			}
		}
		
		return image;
	}
	
	public BufferedImage sepiaFilter(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int pixel = image.getRGB(i, j);
				int a = (pixel >> 24) & 0xFF;
				int r = (pixel >> 16) & 0xFF;
				int g = (pixel >> 8) & 0xFF;
				int b = (pixel) & 0xFF;

				int tr = (int) ((0.393 * r) + (0.769 * g) + (0.189 * b));
				int tg = (int) ((0.349 * r) + (0.686 * g) + (0.168 * b));
				int tb = (int) ((0.272 * r) + (0.534 * g) + (0.131 * b));

				if(tr > 255) {
					r = 255;
				}
				else r = tr;
				
				if(tg > 255) {
					g = 255;
				}
				else g = tg;
				
				if(tb > 255) {
					b = 255;
				}
				else b = tb;
				
				pixel = (a << 24) | (r << 16) | (g << 8) | (b);
				image.setRGB(i, j, pixel);
			}
		}
		
		return image;
	}
	
	public BufferedImage kMeans(BufferedImage image, int k) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage alteredImage = new BufferedImage(width, height, image.getType());
		Graphics2D graphics = alteredImage.createGraphics();
		graphics.drawImage(image, 0, 0, width, height, null);
		
		int[] imageRGB = new int[(width * height)];
		int counter = 0;
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				imageRGB[counter++] = alteredImage.getRGB(i, j);
			}
		}
		
		kMeansAlgorithm(imageRGB, k);
		
		counter = 0;
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				alteredImage.setRGB(i, j, imageRGB[counter++]);
			}
		}
		
		return alteredImage;
	}
	
	public static void kMeansAlgorithm(int[] pixels, int k) {
		
		int[] previousCentroids = new int[k];
		int[] currentCentroids = new int[k];
		int[] numberOfPixels = new int[k];
		int[] numberOfRedInCluster = new int[k];
		int[] numberOfGreenInCluster = new int[k];
		int[] numberOfBlueInCluster = new int[k];
		int[] clusterAssignment = new int[pixels.length];

		double maximumDistance = Double.MAX_VALUE;
		double currentDistance = 0;
		int closestCenter = 0;
		
		for(int i = 0; i < k; i++) {
			Random random = new Random();
			int centerValue = 0;
			centerValue = pixels[random.nextInt(pixels.length)];
			currentCentroids[i] = centerValue;
		}
		
		do {
			for(int i = 0; i < currentCentroids.length; i++) {
				previousCentroids[i] = currentCentroids[i];
				numberOfPixels[i] = 0;
				numberOfRedInCluster[i] = 0;
				numberOfGreenInCluster[i] = 0;
				numberOfBlueInCluster[i] = 0;	
			}
			
			for(int i = 0; i < pixels.length; i++) {
				maximumDistance = Double.MAX_VALUE;
				
				for(int j = 0; j < currentCentroids.length; j++) {
					currentDistance = calculatePixelDistance(pixels[i], currentCentroids[j]);
					if(currentDistance < maximumDistance) {
						maximumDistance = currentDistance;
						closestCenter = j;
					}
				}
				
				clusterAssignment[i] = closestCenter;
				numberOfPixels[closestCenter]++;
				numberOfRedInCluster[closestCenter] += ((pixels[i] & 0x00FF0000) >>> 16);
				numberOfGreenInCluster[closestCenter] += ((pixels[i] & 0x0000FF00) >>> 8);;
				numberOfBlueInCluster[closestCenter] += ((pixels[i] & 0x000000FF) >>> 0);;
			}
			
			for(int i = 0; i < currentCentroids.length; i++) {
				int averageOfRed = (int)((double)numberOfRedInCluster[i] / (double)numberOfPixels[i]);
				int averageOfGreen = (int)((double)numberOfGreenInCluster[i] / (double)numberOfPixels[i]);
				int averageOfBlue = (int)((double)numberOfBlueInCluster[i] / (double)numberOfPixels[i]);
				
				currentCentroids[i] = (((averageOfRed & 0x000000FF) << 16) | ((averageOfGreen & 0x000000FF) << 8) | ((averageOfBlue & 0x000000FF) << 0));
			}
			
		} while (!isConverged(previousCentroids, currentCentroids));
	}
	
	public static boolean isConverged(int[] previousCentroids, int[] currentCentroids) {
		
		for(int i = 0; i < currentCentroids.length; i++) {
			if(previousCentroids[i] != currentCentroids[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	public static double calculatePixelDistance(int pixelA, int pixelB) {
		int differenceOfRed = ((pixelA & 0x00FF0000) >>> 16) - ((pixelB & 0x00FF0000) >>> 16); 
		int differenceOfGreen  = ((pixelA & 0x0000FF00) >>> 8) - ((pixelB & 0x0000FF00) >>> 8);
		int differenceOfBlue  = ((pixelA & 0x000000FF) >>> 0) - ((pixelB & 0x000000FF) >>> 0);
		
		return Math.sqrt((differenceOfRed * differenceOfRed) + (differenceOfGreen * differenceOfGreen) + (differenceOfBlue * differenceOfBlue));
	}
}
