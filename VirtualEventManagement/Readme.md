
# 🎉 Event Management System 🎉

A **Java-based desktop application** for efficiently managing events! This application includes features to **Add**, **View**, **Search**, **Update**, **Delete**, and **Export Events** with ease.  

---

## 🚀 Features

1. **✨ Add Events**:  
   - Add new events with details like Event Name, Date, Description, Category, Media, and Status.  
   - Upload media files (supported formats: JPG, PNG, GIF).  

2. **👀 View Events**:  
   - Displays events in a table format.  
   - Update or delete events with a single click.  

3. **🔍 Search Events**:  
   - Search events by their name using a powerful search functionality.  

4. **📤 Export to CSV**:  
   - Export all event data to a CSV file in just one click!  

5. **📅 Event Status Management**:  
   - Categorize events into **"Upcoming"**, **"Ongoing"**, and **"Completed"**.  

---

## 🛠 Technology Stack

- **💻 Programming Language**: Java  
- **📊 Database**: MySQL  
- **🎨 UI Framework**: Swing  

---

## 📖 Setup Instructions  

### ⚙️ Prerequisites  
- Java Development Kit (JDK) installed.  
- MySQL Server installed.  
- IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code).  

### 🗄 Database Setup  
1. Create a MySQL database named `event_management`.  
2. Use the following SQL script to create the `events` table:  

```sql
CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(255) NOT NULL,
    event_date DATE NOT NULL,
    event_description TEXT,
    category VARCHAR(50),
    media_path VARCHAR(255),
    status VARCHAR(50)
);
```  

### 🏃‍♂️ How to Run  
1. Clone or download this repository.  
2. Open the project in your preferred IDE.  
3. Configure the database connection:  
   - Update `DB_URL`, `DB_USER`, and `DB_PASSWORD` in the code.  
4. Run the `EventManagementApp` class.  

---

## ✨ Usage  

- Use the **`Add Event`** tab to add new events.  
- View all events in the **`View Events`** tab, where you can update or delete events.  
- Search for events by name in the **`Search Events`** tab.  
- Export all events to a CSV file using the export button.  

---

## 📸 Screenshots  

### 🎨 Event Registration Page  
![Event Registration](screenshots/dbt1.png)  

### 🖥️ View Events Page  
![Home Page](screenshots/dbt2.png)  

### 🔎 Search Events Page  
![Admin Dashboard](screenshots/dbt3.png)  

---
