package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record WeightTable(PublicKey _address,
                          Discriminator discriminator,
                          PublicKey ncn,
                          long epoch,
                          long slotCreated,
                          long vaultCount,
                          int bump,
                          byte[] reserved,
                          VaultEntry[] vaultRegistry,
                          WeightEntry[] table) implements Borsh {

  public static final int BYTES = 37633;
  public static final int RESERVED_LEN = 128;
  public static final int VAULT_REGISTRY_LEN = 64;
  public static final int TABLE_LEN = 64;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NCN_OFFSET = 8;
  public static final int EPOCH_OFFSET = 40;
  public static final int SLOT_CREATED_OFFSET = 48;
  public static final int VAULT_COUNT_OFFSET = 56;
  public static final int BUMP_OFFSET = 64;
  public static final int RESERVED_OFFSET = 65;
  public static final int VAULT_REGISTRY_OFFSET = 193;
  public static final int TABLE_OFFSET = 13505;

  public static Filter createNcnFilter(final PublicKey ncn) {
    return Filter.createMemCompFilter(NCN_OFFSET, ncn);
  }

  public static Filter createEpochFilter(final long epoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epoch);
    return Filter.createMemCompFilter(EPOCH_OFFSET, _data);
  }

  public static Filter createSlotCreatedFilter(final long slotCreated) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotCreated);
    return Filter.createMemCompFilter(SLOT_CREATED_OFFSET, _data);
  }

  public static Filter createVaultCountFilter(final long vaultCount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, vaultCount);
    return Filter.createMemCompFilter(VAULT_COUNT_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static WeightTable read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static WeightTable read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static WeightTable read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], WeightTable> FACTORY = WeightTable::read;

  public static WeightTable read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var ncn = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var slotCreated = getInt64LE(_data, i);
    i += 8;
    final var vaultCount = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var reserved = new byte[128];
    i += Borsh.readArray(reserved, _data, i);
    final var vaultRegistry = new VaultEntry[64];
    i += Borsh.readArray(vaultRegistry, VaultEntry::read, _data, i);
    final var table = new WeightEntry[64];
    Borsh.readArray(table, WeightEntry::read, _data, i);
    return new WeightTable(_address,
                           discriminator,
                           ncn,
                           epoch,
                           slotCreated,
                           vaultCount,
                           bump,
                           reserved,
                           vaultRegistry,
                           table);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    ncn.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);
    i += 8;
    putInt64LE(_data, i, slotCreated);
    i += 8;
    putInt64LE(_data, i, vaultCount);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeArrayChecked(reserved, 128, _data, i);
    i += Borsh.writeArrayChecked(vaultRegistry, 64, _data, i);
    i += Borsh.writeArrayChecked(table, 64, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
