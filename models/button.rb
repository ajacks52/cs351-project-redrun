class Button < ActiveRecord::Base
  belongs_to :kiosk
  belongs_to :trap
  I18n.enforce_available_locales = true
end