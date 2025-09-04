package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

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
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record OracleStatsAccountData(PublicKey _address,
                                     Discriminator discriminator,
                                     PublicKey owner,
                                     PublicKey oracle,
                                     // The last epoch that has completed. cleared after registered with the
                                     // staking program.
                                     OracleEpochInfo finalizedEpoch,
                                     // The current epoch info being used by the oracle. for stake. Will moved
                                     // to finalized_epoch as soon as the epoch is over.
                                     OracleEpochInfo currentEpoch,
                                     MegaSlotInfo megaSlotInfo,
                                     long lastTransferSlot,
                                     int bump,
                                     byte[] padding1,
                                     // Reserved.
                                     byte[] ebuf) implements Borsh {

  public static final int BYTES = 1240;
  public static final int PADDING_1_LEN = 7;
  public static final int EBUF_LEN = 1024;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(180, 157, 178, 234, 240, 27, 152, 179);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int OWNER_OFFSET = 8;
  public static final int ORACLE_OFFSET = 40;
  public static final int FINALIZED_EPOCH_OFFSET = 72;
  public static final int CURRENT_EPOCH_OFFSET = 120;
  public static final int MEGA_SLOT_INFO_OFFSET = 168;
  public static final int LAST_TRANSFER_SLOT_OFFSET = 200;
  public static final int BUMP_OFFSET = 208;
  public static final int PADDING_1_OFFSET = 209;
  public static final int EBUF_OFFSET = 216;

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createOracleFilter(final PublicKey oracle) {
    return Filter.createMemCompFilter(ORACLE_OFFSET, oracle);
  }

  public static Filter createFinalizedEpochFilter(final OracleEpochInfo finalizedEpoch) {
    return Filter.createMemCompFilter(FINALIZED_EPOCH_OFFSET, finalizedEpoch.write());
  }

  public static Filter createCurrentEpochFilter(final OracleEpochInfo currentEpoch) {
    return Filter.createMemCompFilter(CURRENT_EPOCH_OFFSET, currentEpoch.write());
  }

  public static Filter createMegaSlotInfoFilter(final MegaSlotInfo megaSlotInfo) {
    return Filter.createMemCompFilter(MEGA_SLOT_INFO_OFFSET, megaSlotInfo.write());
  }

  public static Filter createLastTransferSlotFilter(final long lastTransferSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastTransferSlot);
    return Filter.createMemCompFilter(LAST_TRANSFER_SLOT_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static OracleStatsAccountData read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static OracleStatsAccountData read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static OracleStatsAccountData read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], OracleStatsAccountData> FACTORY = OracleStatsAccountData::read;

  public static OracleStatsAccountData read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var owner = readPubKey(_data, i);
    i += 32;
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var finalizedEpoch = OracleEpochInfo.read(_data, i);
    i += Borsh.len(finalizedEpoch);
    final var currentEpoch = OracleEpochInfo.read(_data, i);
    i += Borsh.len(currentEpoch);
    final var megaSlotInfo = MegaSlotInfo.read(_data, i);
    i += Borsh.len(megaSlotInfo);
    final var lastTransferSlot = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var padding1 = new byte[7];
    i += Borsh.readArray(padding1, _data, i);
    final var ebuf = new byte[1024];
    Borsh.readArray(ebuf, _data, i);
    return new OracleStatsAccountData(_address,
                                      discriminator,
                                      owner,
                                      oracle,
                                      finalizedEpoch,
                                      currentEpoch,
                                      megaSlotInfo,
                                      lastTransferSlot,
                                      bump,
                                      padding1,
                                      ebuf);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    owner.write(_data, i);
    i += 32;
    oracle.write(_data, i);
    i += 32;
    i += Borsh.write(finalizedEpoch, _data, i);
    i += Borsh.write(currentEpoch, _data, i);
    i += Borsh.write(megaSlotInfo, _data, i);
    putInt64LE(_data, i, lastTransferSlot);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeArray(padding1, _data, i);
    i += Borsh.writeArray(ebuf, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
