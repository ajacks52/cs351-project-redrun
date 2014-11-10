require 'active_record'

# Migration to create the Buttons tables
class CreateButtons < ActiveRecord::Migration

  def change
    create_table :buttons do |t|
      t.column :state, :boolean, default: false
      t.belongs_to :kiosk, null: false
      t.belongs_to :trap, null: false
    end
    add_index :buttons, [:state], :name => "index_buttons_on_state"
    add_index :buttons, [:kiosk_id], :name => "index_buttons_on_kiosk_id"
    add_index :buttons, [:trap_id], :name => "index_buttons_on_trap_id"
  end
end