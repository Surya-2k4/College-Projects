#pip install yt-dlp

from yt_dlp import YoutubeDL

def list_video_formats(url):
    """
    Lists available video formats for a YouTube video.

    Args:
        url (str): The URL of the YouTube video.

    Returns:
        list: A list of available formats with their resolutions and format IDs.
    """
    try:
        ydl_opts = {'listformats': True}
        with YoutubeDL(ydl_opts) as ydl:
            info_dict = ydl.extract_info(url, download=False)
            formats = info_dict.get('formats', [])
            print("\nAvailable Formats:")
            available_formats = []
            for f in formats:
                if f.get('vcodec') != 'none':  # Filter only video formats
                    resolution = f.get('height', 'audio only')
                    format_id = f.get('format_id')
                    print(f"ID: {format_id}, Resolution: {resolution}p, Format: {f.get('ext')}")
                    available_formats.append({'id': format_id, 'resolution': resolution})
            return available_formats
    except Exception as e:
        print(f"An error occurred while fetching formats: {e}")
        return []

def download_youtube_video(url, format_id):
    """
    Downloads a YouTube video in the selected format using yt-dlp.

    Args:
        url (str): The URL of the YouTube video.
        format_id (str): The format ID of the desired video quality.
    """
    try:
        ydl_opts = {
            'format': format_id,
            'outtmpl': '%(title)s.%(ext)s',  # Save file with video title
            'progress_hooks': [lambda d: print(f"Status: {d['status']}")],
        }
        with YoutubeDL(ydl_opts) as ydl:
            print(f"Downloading video in format ID {format_id}...")
            ydl.download([url])
            print("Download complete!")
    except Exception as e:
        print(f"An error occurred: {e}")

if __name__ == "__main__":
    url = input("Enter the YouTube video URL: ")
    available_formats = list_video_formats(url)
    if available_formats:
        print("\nChoose a format ID from the list above:")
        format_id = input("Enter the desired format ID: ")
        download_youtube_video(url, format_id)
    else:
        print("No formats available for download.")
