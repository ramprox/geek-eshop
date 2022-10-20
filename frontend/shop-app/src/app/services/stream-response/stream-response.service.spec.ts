import { TestBed } from '@angular/core/testing';

import { StreamResponseService } from './stream-response.service';

describe('StreamResponseService', () => {
  let service: StreamResponseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StreamResponseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
