export interface Department {
  id?: number;         // optional for new countries before saving
  name: string;
  designation?: number[]; // list of division IDs (optional)
}