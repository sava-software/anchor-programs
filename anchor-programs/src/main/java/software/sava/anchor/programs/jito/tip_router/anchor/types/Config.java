package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Config(PublicKey _address,
                     Discriminator discriminator,
                     PublicKey ncn,
                     PublicKey tieBreakerAdmin,
                     PublicKey feeAdmin,
                     long validSlotsAfterConsensus,
                     long epochsBeforeStall,
                     FeeConfig feeConfig,
                     int bump,
                     long epochsAfterConsensusBeforeClose,
                     long startingValidEpoch,
                     byte[] reserved) implements Borsh {

  public static final int BYTES = 970;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NCN_OFFSET = 8;
  public static final int TIE_BREAKER_ADMIN_OFFSET = 40;
  public static final int FEE_ADMIN_OFFSET = 72;
  public static final int VALID_SLOTS_AFTER_CONSENSUS_OFFSET = 104;
  public static final int EPOCHS_BEFORE_STALL_OFFSET = 112;
  public static final int FEE_CONFIG_OFFSET = 120;
  public static final int BUMP_OFFSET = 842;
  public static final int EPOCHS_AFTER_CONSENSUS_BEFORE_CLOSE_OFFSET = 843;
  public static final int STARTING_VALID_EPOCH_OFFSET = 851;
  public static final int RESERVED_OFFSET = 859;

  public static Filter createNcnFilter(final PublicKey ncn) {
    return Filter.createMemCompFilter(NCN_OFFSET, ncn);
  }

  public static Filter createTieBreakerAdminFilter(final PublicKey tieBreakerAdmin) {
    return Filter.createMemCompFilter(TIE_BREAKER_ADMIN_OFFSET, tieBreakerAdmin);
  }

  public static Filter createFeeAdminFilter(final PublicKey feeAdmin) {
    return Filter.createMemCompFilter(FEE_ADMIN_OFFSET, feeAdmin);
  }

  public static Filter createValidSlotsAfterConsensusFilter(final long validSlotsAfterConsensus) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, validSlotsAfterConsensus);
    return Filter.createMemCompFilter(VALID_SLOTS_AFTER_CONSENSUS_OFFSET, _data);
  }

  public static Filter createEpochsBeforeStallFilter(final long epochsBeforeStall) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epochsBeforeStall);
    return Filter.createMemCompFilter(EPOCHS_BEFORE_STALL_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createEpochsAfterConsensusBeforeCloseFilter(final long epochsAfterConsensusBeforeClose) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epochsAfterConsensusBeforeClose);
    return Filter.createMemCompFilter(EPOCHS_AFTER_CONSENSUS_BEFORE_CLOSE_OFFSET, _data);
  }

  public static Filter createStartingValidEpochFilter(final long startingValidEpoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, startingValidEpoch);
    return Filter.createMemCompFilter(STARTING_VALID_EPOCH_OFFSET, _data);
  }

  public static Config read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Config read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Config read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Config> FACTORY = Config::read;

  public static Config read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var ncn = readPubKey(_data, i);
    i += 32;
    final var tieBreakerAdmin = readPubKey(_data, i);
    i += 32;
    final var feeAdmin = readPubKey(_data, i);
    i += 32;
    final var validSlotsAfterConsensus = getInt64LE(_data, i);
    i += 8;
    final var epochsBeforeStall = getInt64LE(_data, i);
    i += 8;
    final var feeConfig = FeeConfig.read(_data, i);
    i += Borsh.len(feeConfig);
    final var bump = _data[i] & 0xFF;
    ++i;
    final var epochsAfterConsensusBeforeClose = getInt64LE(_data, i);
    i += 8;
    final var startingValidEpoch = getInt64LE(_data, i);
    i += 8;
    final var reserved = new byte[111];
    Borsh.readArray(reserved, _data, i);
    return new Config(_address,
                      discriminator,
                      ncn,
                      tieBreakerAdmin,
                      feeAdmin,
                      validSlotsAfterConsensus,
                      epochsBeforeStall,
                      feeConfig,
                      bump,
                      epochsAfterConsensusBeforeClose,
                      startingValidEpoch,
                      reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    ncn.write(_data, i);
    i += 32;
    tieBreakerAdmin.write(_data, i);
    i += 32;
    feeAdmin.write(_data, i);
    i += 32;
    putInt64LE(_data, i, validSlotsAfterConsensus);
    i += 8;
    putInt64LE(_data, i, epochsBeforeStall);
    i += 8;
    i += Borsh.write(feeConfig, _data, i);
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, epochsAfterConsensusBeforeClose);
    i += 8;
    putInt64LE(_data, i, startingValidEpoch);
    i += 8;
    i += Borsh.writeArray(reserved, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
