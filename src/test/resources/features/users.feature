Feature: 회원 생성, 전체 회원 조회, 특정 회원 조회, 회원 수정, 회원 삭제를 한다.
  Scenario Outline: 특정 회원 조회
    When 조회할 회원의 ID "<id>" 입력받는다"
    Then status 코드 "<status>"을 수신한다
    And user 정보를 수신한다
    Examples:
      | id | status
      | 3  | 200

  Scenario Outline: 전체 회원 조회
    When 클라이언트가 전체회사목록을 요청한다
    Then status 코드 "<status>"을 수신한다
    And n"<n>"명의 user를 수신한다
    Examples:
      | id | status
      | 7  | 200

  Scenario Outline: 회원 생성
    Given 생성할 회원의 정보 "<name>" "<password>"
    When 입력 받은 정보로 회원을 생성한다
    Then status 코드 "<status>"을 수신한다
    And user "<name>" 정보를 수신한다
    Examples:
      | name  | password   | status |
      | user7 | password   | 200  |

  Scenario Outline: 회원 수정
    Given 수정할 회원의 "<name>" "<password>" 입력받는다"
    When 입력 받은 ID "<id>" 정보로 회원을 수정한다
    Then status 코드 "<status>"을 수신한다
    And 수정된 user "<name>" 정보를 수신한다
    Examples:
      | id | name  | password    | status |
      | 18 | new   | new_password| 200    |

  Scenario Outline: 회원 삭제
    When 삭제할 회원의 "<id>" 입력받는다
    Then status 코드 "<status>"을 수신한다
    Examples:
      | id | status
      | 13  | 200

