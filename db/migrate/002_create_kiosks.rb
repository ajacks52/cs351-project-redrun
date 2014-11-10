require 'active_record'

# Migration to create the Kiosk tables
class CreateKiosks < ActiveRecord::Migration

  def change
    create_table :kiosks do |t|
      t.column :location, :string, null: false
      t.column :cooldown, :decimal, null: false
      t.belongs_to :trap, null: false
      t.belongs_to :button, null: false
    end
    add_index :kiosks, [:location], :name => "index_kiosks_on_location"
    add_index :kiosks, [:cooldown], :name => "index_kiosks_on_cooldown"
    add_index :kiosks, [:trap_id], :name => "index_kiosks_on_trap_id"
    add_index :kiosks, [:button_id], :name => "index_kiosks_on_button_id"
  end
end