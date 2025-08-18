package software.sava.anchor.programs.loopscale.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record Vault(PublicKey _address,
                    Discriminator discriminator,
                    PublicKey manager,
                    PublicKey nonce,
                    int bump,
                    PodU64 lpSupply,
                    PublicKey lpMint,
                    PublicKey principalMint,
                    PodU64 cumulativePrincipalDeposited,
                    PodBool depositsEnabled,
                    PodU64CBPS maxEarlyUnstakeFee) implements Borsh {

  public static final int BYTES = 162;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(211, 8, 232, 43, 2, 152, 117, 119);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int MANAGER_OFFSET = 8;
  public static final int NONCE_OFFSET = 40;
  public static final int BUMP_OFFSET = 72;
  public static final int LP_SUPPLY_OFFSET = 73;
  public static final int LP_MINT_OFFSET = 81;
  public static final int PRINCIPAL_MINT_OFFSET = 113;
  public static final int CUMULATIVE_PRINCIPAL_DEPOSITED_OFFSET = 145;
  public static final int DEPOSITS_ENABLED_OFFSET = 153;
  public static final int MAX_EARLY_UNSTAKE_FEE_OFFSET = 154;

  public static Filter createManagerFilter(final PublicKey manager) {
    return Filter.createMemCompFilter(MANAGER_OFFSET, manager);
  }

  public static Filter createNonceFilter(final PublicKey nonce) {
    return Filter.createMemCompFilter(NONCE_OFFSET, nonce);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createLpSupplyFilter(final PodU64 lpSupply) {
    return Filter.createMemCompFilter(LP_SUPPLY_OFFSET, lpSupply.write());
  }

  public static Filter createLpMintFilter(final PublicKey lpMint) {
    return Filter.createMemCompFilter(LP_MINT_OFFSET, lpMint);
  }

  public static Filter createPrincipalMintFilter(final PublicKey principalMint) {
    return Filter.createMemCompFilter(PRINCIPAL_MINT_OFFSET, principalMint);
  }

  public static Filter createCumulativePrincipalDepositedFilter(final PodU64 cumulativePrincipalDeposited) {
    return Filter.createMemCompFilter(CUMULATIVE_PRINCIPAL_DEPOSITED_OFFSET, cumulativePrincipalDeposited.write());
  }

  public static Filter createDepositsEnabledFilter(final PodBool depositsEnabled) {
    return Filter.createMemCompFilter(DEPOSITS_ENABLED_OFFSET, depositsEnabled.write());
  }

  public static Filter createMaxEarlyUnstakeFeeFilter(final PodU64CBPS maxEarlyUnstakeFee) {
    return Filter.createMemCompFilter(MAX_EARLY_UNSTAKE_FEE_OFFSET, maxEarlyUnstakeFee.write());
  }

  public static Vault read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Vault read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Vault read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Vault> FACTORY = Vault::read;

  public static Vault read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var manager = readPubKey(_data, i);
    i += 32;
    final var nonce = readPubKey(_data, i);
    i += 32;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var lpSupply = PodU64.read(_data, i);
    i += Borsh.len(lpSupply);
    final var lpMint = readPubKey(_data, i);
    i += 32;
    final var principalMint = readPubKey(_data, i);
    i += 32;
    final var cumulativePrincipalDeposited = PodU64.read(_data, i);
    i += Borsh.len(cumulativePrincipalDeposited);
    final var depositsEnabled = PodBool.read(_data, i);
    i += Borsh.len(depositsEnabled);
    final var maxEarlyUnstakeFee = PodU64CBPS.read(_data, i);
    return new Vault(_address,
                     discriminator,
                     manager,
                     nonce,
                     bump,
                     lpSupply,
                     lpMint,
                     principalMint,
                     cumulativePrincipalDeposited,
                     depositsEnabled,
                     maxEarlyUnstakeFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    manager.write(_data, i);
    i += 32;
    nonce.write(_data, i);
    i += 32;
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.write(lpSupply, _data, i);
    lpMint.write(_data, i);
    i += 32;
    principalMint.write(_data, i);
    i += 32;
    i += Borsh.write(cumulativePrincipalDeposited, _data, i);
    i += Borsh.write(depositsEnabled, _data, i);
    i += Borsh.write(maxEarlyUnstakeFee, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
