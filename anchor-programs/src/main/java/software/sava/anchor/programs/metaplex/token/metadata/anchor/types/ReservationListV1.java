package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.util.OptionalLong;
import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ReservationListV1(PublicKey _address,
                                Key key,
                                PublicKey masterEdition,
                                OptionalLong supplySnapshot,
                                ReservationV1[] reservations) implements Borsh {

  public static final int KEY_OFFSET = 0;
  public static final int MASTER_EDITION_OFFSET = 1;
  public static final int SUPPLY_SNAPSHOT_OFFSET = 33;

  public static Filter createKeyFilter(final Key key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key.write());
  }

  public static Filter createMasterEditionFilter(final PublicKey masterEdition) {
    return Filter.createMemCompFilter(MASTER_EDITION_OFFSET, masterEdition);
  }

  public static Filter createSupplySnapshotFilter(final long supplySnapshot) {
    final byte[] _data = new byte[9];
    _data[0] = 1;
    putInt64LE(_data, 1, supplySnapshot);
    return Filter.createMemCompFilter(SUPPLY_SNAPSHOT_OFFSET, _data);
  }

  public static ReservationListV1 read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ReservationListV1 read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ReservationListV1 read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ReservationListV1> FACTORY = ReservationListV1::read;

  public static ReservationListV1 read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = Key.read(_data, i);
    i += Borsh.len(key);
    final var masterEdition = readPubKey(_data, i);
    i += 32;
    final var supplySnapshot = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (supplySnapshot.isPresent()) {
      i += 8;
    }
    final var reservations = Borsh.readVector(ReservationV1.class, ReservationV1::read, _data, i);
    return new ReservationListV1(_address,
                                 key,
                                 masterEdition,
                                 supplySnapshot,
                                 reservations);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(key, _data, i);
    masterEdition.write(_data, i);
    i += 32;
    i += Borsh.writeOptional(supplySnapshot, _data, i);
    i += Borsh.writeVector(reservations, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(key) + 32 + (supplySnapshot == null || supplySnapshot.isEmpty() ? 1 : (1 + 8)) + Borsh.lenVector(reservations);
  }
}
