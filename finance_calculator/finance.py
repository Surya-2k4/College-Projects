import tkinter as tk
from tkinter import ttk

# Color and Font Definitions
COLOR_BACKGROUND = "#1c1c1c"
COLOR_PRIMARY = "#4caf50"
COLOR_SECONDARY = "#ff9800"
COLOR_FOREGROUND = "#ffffff"
FONT_TITLE = ("Arial", 16, "bold")
FONT_LABEL = ("Arial", 12)
FONT_RESULT = ("Arial", 12, "bold")

# Root Configuration
root = tk.Tk()
root.title("Finance Calculators")
root.configure(bg=COLOR_BACKGROUND)
root.geometry("1000x800")  # Set a specific size (Width x Height)
#root.attributes('-fullscreen', True)  # For full-screen view


# Main Frame
main_frame = tk.Frame(root, bg=COLOR_BACKGROUND)
main_frame.pack(fill="both", expand=True, padx=10, pady=10, side="top")


# Result Variables
si_result = tk.StringVar()
ci_result = tk.StringVar()
emi_result = tk.StringVar()
sip_result = tk.StringVar()
roi_result = tk.StringVar()

# Functions for Calculators
def calculate_si():
    try:
        principal = float(entry_si_principal.get())
        rate = float(entry_si_rate.get())
        time = float(entry_si_time.get())
        si = (principal * rate * time) / 100
        si_result.set(f"Simple Interest: ₹{si:.2f}")
    except ValueError:
        si_result.set("Invalid Input!")

def calculate_ci():
    try:
        principal = float(entry_ci_principal.get())
        rate = float(entry_ci_rate.get())
        time = float(entry_ci_time.get())
        ci = principal * ((1 + (rate / 100)) ** time) - principal
        ci_result.set(f"Compound Interest: ₹{ci:.2f}")
    except ValueError:
        ci_result.set("Invalid Input!")

def calculate_loan_emi():
    try:
        loan_amount = float(entry_loan_amount.get())
        rate = float(entry_loan_rate.get()) / (12 * 100)  # Monthly interest rate
        duration = int(entry_loan_duration.get()) * 12   # Duration in months
        emi = loan_amount * rate * ((1 + rate) ** duration) / (((1 + rate) ** duration) - 1)
        emi_result.set(f"EMI: ₹{emi:.2f}")
    except ValueError:
        emi_result.set("Invalid Input!")

def calculate_sip():
    try:
        investment = float(entry_sip_investment.get())
        rate = float(entry_sip_rate.get()) / 12 / 100  # Monthly rate
        months = int(entry_sip_months.get())
        maturity = investment * (((1 + rate) ** months - 1) / rate) * (1 + rate)
        sip_result.set(f"SIP Maturity: ₹{maturity:.2f}")
    except ValueError:
        sip_result.set("Invalid Input!")

def calculate_roi():
    try:
        initial = float(entry_roi_initial.get())
        final = float(entry_roi_final.get())
        roi = ((final - initial) / initial) * 100
        roi_result.set(f"ROI: {roi:.2f}%")
    except ValueError:
        roi_result.set("Invalid Input!")

# Layout: Simple Interest
si_frame = tk.Frame(main_frame, bg=COLOR_BACKGROUND)
si_frame.grid(row=0, column=0, padx=20, pady=20, sticky="nsew")

tk.Label(si_frame, text="Simple Interest Calculator", font=FONT_TITLE, bg=COLOR_BACKGROUND, fg=COLOR_PRIMARY).pack(pady=10)
tk.Label(si_frame, text="Principal (₹):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_si_principal = ttk.Entry(si_frame, font=FONT_LABEL)
entry_si_principal.pack(fill="x", pady=5)
tk.Label(si_frame, text="Rate of Interest (%):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_si_rate = ttk.Entry(si_frame, font=FONT_LABEL)
entry_si_rate.pack(fill="x", pady=5)
tk.Label(si_frame, text="Time (Years):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_si_time = ttk.Entry(si_frame, font=FONT_LABEL)
entry_si_time.pack(fill="x", pady=5)
tk.Button(si_frame, text="Calculate SI", command=calculate_si, font=FONT_LABEL, bg=COLOR_SECONDARY, fg=COLOR_FOREGROUND).pack(pady=10)
tk.Label(si_frame, textvariable=si_result, font=FONT_RESULT, bg=COLOR_BACKGROUND, fg=COLOR_PRIMARY).pack()

# Layout: Compound Interest
ci_frame = tk.Frame(main_frame, bg=COLOR_BACKGROUND)
ci_frame.grid(row=0, column=1, padx=20, pady=20, sticky="nsew")

tk.Label(ci_frame, text="Compound Interest Calculator", font=FONT_TITLE, bg=COLOR_BACKGROUND, fg=COLOR_PRIMARY).pack(pady=10)
tk.Label(ci_frame, text="Principal (₹):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_ci_principal = ttk.Entry(ci_frame, font=FONT_LABEL)
entry_ci_principal.pack(fill="x", pady=5)
tk.Label(ci_frame, text="Rate of Interest (%):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_ci_rate = ttk.Entry(ci_frame, font=FONT_LABEL)
entry_ci_rate.pack(fill="x", pady=5)
tk.Label(ci_frame, text="Time (Years):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_ci_time = ttk.Entry(ci_frame, font=FONT_LABEL)
entry_ci_time.pack(fill="x", pady=5)
tk.Button(ci_frame, text="Calculate CI", command=calculate_ci, font=FONT_LABEL, bg=COLOR_SECONDARY, fg=COLOR_FOREGROUND).pack(pady=10)
tk.Label(ci_frame, textvariable=ci_result, font=FONT_RESULT, bg=COLOR_BACKGROUND, fg=COLOR_PRIMARY).pack()

# Repeat similar for Loan EMI, SIP, and ROI...

# SIP Section
sip_frame = tk.Frame(main_frame, bg=COLOR_BACKGROUND)
sip_frame.grid(row=1, column=0, padx=20, pady=20, sticky="nsew")

sip_title = tk.Label(sip_frame, text="SIP Calculator", font=FONT_TITLE, bg=COLOR_BACKGROUND, fg=COLOR_PRIMARY)
sip_title.pack(pady=10)

tk.Label(sip_frame, text="Monthly Investment (₹):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_sip_investment = ttk.Entry(sip_frame, font=FONT_LABEL)
entry_sip_investment.pack(fill="x", pady=5)

tk.Label(sip_frame, text="Annual Interest Rate (%):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_sip_rate = ttk.Entry(sip_frame, font=FONT_LABEL)
entry_sip_rate.pack(fill="x", pady=5)

tk.Label(sip_frame, text="Duration (Months):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_sip_months = ttk.Entry(sip_frame, font=FONT_LABEL)
entry_sip_months.pack(fill="x", pady=5)

btn_calculate_sip = tk.Button(
    sip_frame, text="Calculate SIP", bg=COLOR_SECONDARY, fg="white", font=FONT_LABEL, relief="flat", command=calculate_sip
)
btn_calculate_sip.pack(pady=20)

tk.Label(sip_frame, textvariable=sip_result, font=FONT_RESULT, bg=COLOR_BACKGROUND, fg=COLOR_PRIMARY).pack()

# ROI Section
roi_frame = tk.Frame(main_frame, bg=COLOR_BACKGROUND)
roi_frame.grid(row=1, column=1, padx=20, pady=20, sticky="nsew")

roi_title = tk.Label(roi_frame, text="ROI Calculator", font=FONT_TITLE, bg=COLOR_BACKGROUND, fg=COLOR_PRIMARY)
roi_title.pack(pady=10)

tk.Label(roi_frame, text="Initial Investment (₹):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_roi_initial = ttk.Entry(roi_frame, font=FONT_LABEL)
entry_roi_initial.pack(fill="x", pady=5)

tk.Label(roi_frame, text="Final Value (₹):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_roi_final = ttk.Entry(roi_frame, font=FONT_LABEL)
entry_roi_final.pack(fill="x", pady=5)

btn_calculate_roi = tk.Button(
    roi_frame, text="Calculate ROI", bg=COLOR_SECONDARY, fg="white", font=FONT_LABEL, relief="flat", command=calculate_roi
)
btn_calculate_roi.pack(pady=20)

tk.Label(roi_frame, textvariable=roi_result, font=FONT_RESULT, bg=COLOR_BACKGROUND, fg=COLOR_PRIMARY).pack()

# Loan EMI Section
loan_frame = tk.Frame(main_frame, bg=COLOR_BACKGROUND)
loan_frame.grid(row=0, column=1, padx=20, pady=20, sticky="nsew")

loan_title = tk.Label(loan_frame, text="Loan EMI Calculator", font=FONT_TITLE, bg=COLOR_BACKGROUND, fg=COLOR_PRIMARY)
loan_title.pack(pady=10)

tk.Label(loan_frame, text="Loan Amount (₹):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_loan_amount = ttk.Entry(loan_frame, font=FONT_LABEL)
entry_loan_amount.pack(fill="x", pady=5)

tk.Label(loan_frame, text="Annual Interest Rate (%):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_loan_rate = ttk.Entry(loan_frame, font=FONT_LABEL)
entry_loan_rate.pack(fill="x", pady=5)

tk.Label(loan_frame, text="Loan Duration (Years):", font=FONT_LABEL, bg=COLOR_BACKGROUND, fg=COLOR_FOREGROUND).pack(anchor="w", pady=5)
entry_loan_duration = ttk.Entry(loan_frame, font=FONT_LABEL)
entry_loan_duration.pack(fill="x", pady=5)

btn_calculate_loan = tk.Button(
    loan_frame, text="Calculate EMI", bg=COLOR_SECONDARY, fg="white", font=FONT_LABEL, relief="flat", command=calculate_loan_emi
)
btn_calculate_loan.pack(pady=20)

tk.Label(loan_frame, textvariable=emi_result, font=FONT_RESULT, bg=COLOR_BACKGROUND, fg=COLOR_PRIMARY).pack()


root.mainloop()
