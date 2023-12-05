import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAdvertComponent } from './create-advert.component';

describe('CreateAdvertComponent', () => {
  let component: CreateAdvertComponent;
  let fixture: ComponentFixture<CreateAdvertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateAdvertComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateAdvertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
