// 스케줄 관리 애플리케이션
class ScheduleManager {
    constructor() {
        this.schedules = [];
        this.currentDate = new Date();
        this.editingScheduleId = null;
        this.deletingScheduleId = null;
        this.selectedDate = new Date();
        
        this.init();
    }

    init() {
        this.loadSchedules();
    }

    bindEvents() {
        // 달력 네비게이션
        document.getElementById('prevMonth').addEventListener('click', () => {
            this.currentDate.setMonth(this.currentDate.getMonth() - 1);
            this.renderCalendar();
        });

        document.getElementById('nextMonth').addEventListener('click', () => {
            this.currentDate.setMonth(this.currentDate.getMonth() + 1);
            this.renderCalendar();
        });

        // 스케줄 등록 버튼
        document.getElementById('addScheduleBtn').addEventListener('click', () => {
            this.openScheduleModal(this.selectedDate);
        });

        // 모달 관련 이벤트
        document.querySelectorAll('.close').forEach(closeBtn => {
            closeBtn.addEventListener('click', () => {
                this.closeModals();
            });
        });

        document.getElementById('cancelBtn').addEventListener('click', () => {
            this.closeModals();
        });

        document.getElementById('deleteCancelBtn').addEventListener('click', () => {
            this.closeModals();
        });

        // 스케줄 폼 제출
        document.getElementById('scheduleForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.saveSchedule();
        });

        // 삭제 확인
        document.getElementById('deleteConfirmBtn').addEventListener('click', () => {
            this.deleteSchedule();
        });

        // 모달 외부 클릭 시 닫기
        window.addEventListener('click', (e) => {
            if (e.target.classList.contains('modal')) {
                this.closeModals();
            }
        });
    }

    // 로컬 스토리지에서 스케줄 로드
    loadSchedules() {
        fetch("/api/schedules/" + sessionStorage.getItem('userSeq'), {
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(res => res.json())
            .then(data => {
                data.forEach(schedule => {
                    const dateAndTime = schedule.scheduleDate.split(" ");
                    const date = dateAndTime[0];
                    const time = dateAndTime[1];

                    delete schedule.scheduleDate;
                    schedule.date = date;
                    schedule.time = time;
                })
                this.schedules = data;
                this.renderCalendar();
                this.bindEvents();
                this.renderSelectedDateSchedules();
            });
    }

    // 스케줄 저장
    saveSchedules() {
        localStorage.setItem('schedules', JSON.stringify(this.schedules));
    }

    // 달력 렌더링
    renderCalendar() {
        const calendar = document.getElementById('calendar');
        const currentMonth = document.getElementById('currentMonth');
        
        // 현재 월 표시
        const monthNames = ['1월', '2월', '3월', '4월', '5월', '6월', 
                           '7월', '8월', '9월', '10월', '11월', '12월'];
        currentMonth.textContent = `${this.currentDate.getFullYear()}년 ${monthNames[this.currentDate.getMonth()]}`;

        // 달력 초기화
        calendar.innerHTML = '';

        // 요일 헤더
        const dayHeaders = ['일', '월', '화', '수', '목', '금', '토'];
        dayHeaders.forEach(day => {
            const dayHeader = document.createElement('div');
            dayHeader.className = 'calendar-day-header';
            dayHeader.textContent = day;
            dayHeader.style.cssText = 'background: #4a5568; color: white; padding: 10px; text-align: center; font-weight: 600;';
            calendar.appendChild(dayHeader);
        });

        // 달력 날짜 생성
        const year = this.currentDate.getFullYear();
        const month = this.currentDate.getMonth();
        const firstDay = new Date(year, month, 1);
        const lastDay = new Date(year, month + 1, 0);
        const startDate = new Date(firstDay);
        startDate.setDate(startDate.getDate() - firstDay.getDay());

        for (let i = 0; i < 42; i++) {
            const date = new Date(startDate);
            date.setDate(startDate.getDate() + i);
            
            const dayElement = this.createDayElement(date, month);
            calendar.appendChild(dayElement);
        }
    }

    // 날짜 요소 생성
    createDayElement(date, currentMonth) {
        const dayElement = document.createElement('div');
        dayElement.className = 'calendar-day';
        
        const isCurrentMonth = date.getMonth() === currentMonth;
        const isToday = this.isToday(date);
        const hasSchedules = this.getSchedulesForDate(date).length > 0;

        if (!isCurrentMonth) {
            dayElement.classList.add('other-month');
        }
        if (isToday) {
            dayElement.classList.add('today');
        }
        if (hasSchedules) {
            dayElement.classList.add('has-schedule');
        }

        dayElement.innerHTML = `
            <div class="day-number">${date.getDate()}</div>
            ${hasSchedules ? this.createScheduleIndicators(date) : ''}
        `;

        // 날짜 클릭 이벤트
        dayElement.addEventListener('click', () => {
            this.renderSelectedDateSchedules(date);
            this.selectedDate = date;
        });

        return dayElement;
    }

    // 스케줄 인디케이터 생성
    createScheduleIndicators(date) {
        const schedules = this.getSchedulesForDate(date);
        const indicators = schedules.slice(0, 3).map(schedule => {
            const indicator = document.createElement('div');
            indicator.className = 'schedule-indicator';
            indicator.style.backgroundColor = schedule.color;
            return indicator.outerHTML;
        }).join('');

        if (schedules.length > 3) {
            return indicators + '<div class="schedule-indicator multiple"></div>';
        }
        return indicators;
    }

    // 특정 날짜의 스케줄 가져오기
    getSchedulesForDate(date) {
        const dateStr = this.formatDate(date);
        return this.schedules.filter(schedule => schedule.date === dateStr);
    }

    // 스케줄 렌더링
    renderSelectedDateSchedules(date) {
        const selectedDateScheduleHeader = document.getElementById('selectedDateScheduleHeader');
        const selectedDateSchedule = document.getElementById('selectedDateSchedule');
        const compDate = this.formatDate(date);
        const schedules = this.schedules.filter(schedule => schedule.date === compDate);

        selectedDateScheduleHeader.innerText = compDate;

        if (schedules.length === 0) {
            selectedDateSchedule.innerHTML = '<div class="schedule-item empty">오늘 등록된 스케줄이 없습니다.</div>';
            return;
        }

        // 시간순으로 정렬
        schedules.sort((a, b) => a.time.localeCompare(b.time));

        selectedDateSchedule.innerHTML = schedules.map(schedule => `
            <div class="schedule-item schedule-${this.getColorClass(schedule.color)}" data-id="${schedule.id}">
                <div class="schedule-title">${schedule.title}</div>
                <div class="schedule-time">${schedule.time}</div>
                ${schedule.description ? `<div class="schedule-description">${schedule.description}</div>` : ''}
                <div class="schedule-actions">
                    <button class="btn btn-secondary" onclick="scheduleManager.editSchedule('${schedule.id}')">수정</button>
                    <button class="btn btn-danger" onclick="scheduleManager.confirmDelete('${schedule.id}')">삭제</button>
                </div>
            </div>
        `).join('');
    }

    // 스케줄 모달 열기
    openScheduleModal(selectedDate = null) {
        const modal = document.getElementById('scheduleModal');
        const form = document.getElementById('scheduleForm');
        const modalTitle = document.getElementById('modalTitle');
        
        // 폼 초기화
        form.reset();
        this.editingScheduleId = null;

        if (selectedDate) {
            document.getElementById('scheduleDate').value = this.formatDate(selectedDate);
        } else {
            document.getElementById('scheduleDate').value = this.formatDate(new Date());
        }

        modalTitle.textContent = '스케줄 등록';
        modal.style.display = 'block';
    }

    // 스케줄 수정
    editSchedule(scheduleId) {
        const schedule = this.schedules.find(s => s.id === scheduleId);
        if (!schedule) return;

        this.editingScheduleId = scheduleId;
        const modal = document.getElementById('scheduleModal');
        const modalTitle = document.getElementById('modalTitle');
        
        // 폼에 기존 데이터 채우기
        document.getElementById('scheduleTitle').value = schedule.title;
        document.getElementById('scheduleDate').value = schedule.date;
        document.getElementById('scheduleTime').value = schedule.time;
        document.getElementById('scheduleDescription').value = schedule.description || '';
        document.getElementById('scheduleColor').value = schedule.color;

        modalTitle.textContent = '스케줄 수정';
        modal.style.display = 'block';
    }

    // 스케줄 저장
    saveSchedule() {
        const form = document.getElementById('scheduleForm');
        const formData = new FormData(form);
        
        const scheduleData = {
            id: this.editingScheduleId || this.generateId(),
            title: formData.get('title'),
            date: formData.get('date'),
            time: formData.get('time'),
            description: formData.get('description'),
            color: formData.get('color')
        };

        const userSeq = sessionStorage.getItem("userSeq");

        fetch("/api/schedules", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "userSeq": userSeq,
                "title": scheduleData.title,
                "scheduleDate": scheduleData.date + " " + scheduleData.time,
                "contents": scheduleData.description,
                "colorId": scheduleData.color
            })
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
            })

        if (this.editingScheduleId) {
            // 수정
            const index = this.schedules.findIndex(s => s.id === this.editingScheduleId);
            if (index !== -1) {
                this.schedules[index] = scheduleData;
            }
        } else {
            // 새로 추가
            this.schedules.push(scheduleData);
        }

        this.saveSchedules();
        this.renderCalendar();
        this.renderSelectedDateSchedules();
        this.closeModals();
    }

    // 삭제 확인
    confirmDelete(scheduleId) {
        this.deletingScheduleId = scheduleId;
        document.getElementById('deleteModal').style.display = 'block';
    }

    // 스케줄 삭제
    deleteSchedule() {
        if (this.deletingScheduleId) {
            this.schedules = this.schedules.filter(s => s.id !== this.deletingScheduleId);
            this.saveSchedules();
            this.renderCalendar();
            this.renderSelectedDateSchedules();
        }
        this.closeModals();
    }

    // 모달 닫기
    closeModals() {
        document.getElementById('scheduleModal').style.display = 'none';
        document.getElementById('deleteModal').style.display = 'none';
        this.editingScheduleId = null;
        this.deletingScheduleId = null;
    }

    // 유틸리티 함수들
    formatDate(date) {
        const kstDate = new Date(date.getTime() + 9 * 60 * 60 * 1000);
        return kstDate.toISOString().split('T')[0];
    }

    isToday(date) {
        const today = new Date();
        return date.toDateString() === today.toDateString();
    }

    generateId() {
        return Date.now().toString(36) + Math.random().toString(36).substr(2);
    }

    getColorClass(color) {
        const colorMap = {
            '#ff6b6b': 'red',
            '#4ecdc4': 'teal',
            '#45b7d1': 'blue',
            '#96ceb4': 'green',
            '#feca57': 'yellow',
            '#ff9ff3': 'pink',
            '#a55eea': 'purple'
        };
        return colorMap[color] || 'teal';
    }
}

// 애플리케이션 초기화
const scheduleManager = new ScheduleManager();
