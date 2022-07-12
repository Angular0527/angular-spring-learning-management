import { Injectable } from '@angular/core';
import { Subject } from '@core/models/subject.model';
import { Observable } from 'rxjs';
import { RestService } from './rest.service';

@Injectable({
  providedIn: 'root',
})
export class SubjectService extends RestService<Subject> {
  override url: string = `${this.url}/subject-service/subjects`;

  getByStudyProgramId(id: number): Observable<Subject[]> {
    return this.http.get<Subject[]>(`${this.url}/study-program/${id}/all`);
  }

  getByTeacherUsername(username: string): Observable<Subject[]> {
    return this.http.get<Subject[]>(`${this.url}/teacher/${username}/all`);
  }

  updateSyllabus(id: number, syllabus: string): Observable<Subject> {
    return this.http.patch<Subject>(`${this.url}/${id}/syllabus`, syllabus);
  }
}
