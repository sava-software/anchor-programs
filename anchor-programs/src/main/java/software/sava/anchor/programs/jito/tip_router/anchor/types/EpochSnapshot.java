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

public record EpochSnapshot(PublicKey _address,
                            Discriminator discriminator,
                            PublicKey ncn,
                            long epoch,
                            int bump,
                            long slotCreated,
                            long slotFinalized,
                            Fees fees,
                            long operatorCount,
                            long vaultCount,
                            long operatorsRegistered,
                            long validOperatorVaultDelegations,
                            StakeWeights stakeWeights,
                            byte[] reserved) implements Borsh {

  public static final int BYTES = 537;
  public static final int RESERVED_LEN = 128;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NCN_OFFSET = 8;
  public static final int EPOCH_OFFSET = 40;
  public static final int BUMP_OFFSET = 48;
  public static final int SLOT_CREATED_OFFSET = 49;
  public static final int SLOT_FINALIZED_OFFSET = 57;
  public static final int FEES_OFFSET = 65;
  public static final int OPERATOR_COUNT_OFFSET = 233;
  public static final int VAULT_COUNT_OFFSET = 241;
  public static final int OPERATORS_REGISTERED_OFFSET = 249;
  public static final int VALID_OPERATOR_VAULT_DELEGATIONS_OFFSET = 257;
  public static final int STAKE_WEIGHTS_OFFSET = 265;
  public static final int RESERVED_OFFSET = 409;

  public static Filter createNcnFilter(final PublicKey ncn) {
    return Filter.createMemCompFilter(NCN_OFFSET, ncn);
  }

  public static Filter createEpochFilter(final long epoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epoch);
    return Filter.createMemCompFilter(EPOCH_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createSlotCreatedFilter(final long slotCreated) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotCreated);
    return Filter.createMemCompFilter(SLOT_CREATED_OFFSET, _data);
  }

  public static Filter createSlotFinalizedFilter(final long slotFinalized) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotFinalized);
    return Filter.createMemCompFilter(SLOT_FINALIZED_OFFSET, _data);
  }

  public static Filter createOperatorCountFilter(final long operatorCount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, operatorCount);
    return Filter.createMemCompFilter(OPERATOR_COUNT_OFFSET, _data);
  }

  public static Filter createVaultCountFilter(final long vaultCount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, vaultCount);
    return Filter.createMemCompFilter(VAULT_COUNT_OFFSET, _data);
  }

  public static Filter createOperatorsRegisteredFilter(final long operatorsRegistered) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, operatorsRegistered);
    return Filter.createMemCompFilter(OPERATORS_REGISTERED_OFFSET, _data);
  }

  public static Filter createValidOperatorVaultDelegationsFilter(final long validOperatorVaultDelegations) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, validOperatorVaultDelegations);
    return Filter.createMemCompFilter(VALID_OPERATOR_VAULT_DELEGATIONS_OFFSET, _data);
  }

  public static EpochSnapshot read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static EpochSnapshot read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static EpochSnapshot read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], EpochSnapshot> FACTORY = EpochSnapshot::read;

  public static EpochSnapshot read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var ncn = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var slotCreated = getInt64LE(_data, i);
    i += 8;
    final var slotFinalized = getInt64LE(_data, i);
    i += 8;
    final var fees = Fees.read(_data, i);
    i += Borsh.len(fees);
    final var operatorCount = getInt64LE(_data, i);
    i += 8;
    final var vaultCount = getInt64LE(_data, i);
    i += 8;
    final var operatorsRegistered = getInt64LE(_data, i);
    i += 8;
    final var validOperatorVaultDelegations = getInt64LE(_data, i);
    i += 8;
    final var stakeWeights = StakeWeights.read(_data, i);
    i += Borsh.len(stakeWeights);
    final var reserved = new byte[128];
    Borsh.readArray(reserved, _data, i);
    return new EpochSnapshot(_address,
                             discriminator,
                             ncn,
                             epoch,
                             bump,
                             slotCreated,
                             slotFinalized,
                             fees,
                             operatorCount,
                             vaultCount,
                             operatorsRegistered,
                             validOperatorVaultDelegations,
                             stakeWeights,
                             reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    ncn.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, slotCreated);
    i += 8;
    putInt64LE(_data, i, slotFinalized);
    i += 8;
    i += Borsh.write(fees, _data, i);
    putInt64LE(_data, i, operatorCount);
    i += 8;
    putInt64LE(_data, i, vaultCount);
    i += 8;
    putInt64LE(_data, i, operatorsRegistered);
    i += 8;
    putInt64LE(_data, i, validOperatorVaultDelegations);
    i += 8;
    i += Borsh.write(stakeWeights, _data, i);
    i += Borsh.writeArrayChecked(reserved, 128, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
