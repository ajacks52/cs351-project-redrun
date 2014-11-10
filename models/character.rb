class Character < ActiveRecord::Base
  validates :character_name, presence: true, uniqueness: {case_sensitive: false}
  validates :team, presence: true, uniqueness: {case_sensitive: false}
  I18n.enforce_available_locales = true
end