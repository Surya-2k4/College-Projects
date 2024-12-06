import os
from pathlib import Path
from pdf2image import convert_from_path

def pdf_to_images(pdf_path):
    # Get the PDF name without the path and extension
    pdf_name = Path(pdf_path).stem
    # Get the user's Downloads folder
    downloads_folder = Path.home() / "Downloads"
    output_folder = downloads_folder / pdf_name  # Create a folder with the PDF name
    
    # Create the output folder if it doesn't exist
    output_folder.mkdir(parents=True, exist_ok=True)

    # Convert PDF to images (all pages)
    images = convert_from_path(pdf_path, dpi=300)
    
    # Loop through all pages and save them as images
    for i, image in enumerate(images):
        # Save each image using the PDF name and page number
        image_name = f"{pdf_name}_page_{i + 1}.jpg"
        image_path = output_folder / image_name
        image.save(image_path, "JPEG")
        print(f"Saved: {image_path}")

if __name__ == "__main__":
    pdf_path = input("Enter the path to the PDF file: ").strip()
    try:
        pdf_to_images(pdf_path)
    except Exception as e:
        print(f"An error occurred: {e}")
